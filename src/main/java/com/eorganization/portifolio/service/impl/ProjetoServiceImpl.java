package com.eorganization.portifolio.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eorganization.portifolio.dto.projeto.AtualizaProjetoDTO;
import com.eorganization.portifolio.dto.projeto.CadastroProjetoDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.entity.Cargo;
import com.eorganization.portifolio.entity.Membro;
import com.eorganization.portifolio.entity.ProjectStatus;
import com.eorganization.portifolio.entity.Projeto;
import com.eorganization.portifolio.entity.Usuario;
import com.eorganization.portifolio.exception.ResourceNotFoundException;
import com.eorganization.portifolio.mapper.ProjectMapper;
import com.eorganization.portifolio.repository.MembroRepository;
import com.eorganization.portifolio.repository.NivelRiscoRespository;
import com.eorganization.portifolio.repository.ProjetoRepository;
import com.eorganization.portifolio.service.ProjetoService;

import lombok.NonNull;

@Service
@Transactional
public class ProjetoServiceImpl implements ProjetoService {
    private final ProjetoRepository repo;
    private final NivelRiscoRespository repoRisco;
    private final MembroRepository repoMembro;
    private final ProjectMapper mapper;

    public ProjetoServiceImpl(ProjetoRepository repo, NivelRiscoRespository repoRisco, MembroRepository repoMembro,
            ProjectMapper mapper) {
        this.repo = repo;
        this.repoRisco = repoRisco;
        this.repoMembro = repoMembro;
        this.mapper = mapper;
    }

    @Override
    public ProjetoDTO create(CadastroProjetoDTO dto) {
        Projeto p = mapper.toEntity(dto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        p.setUsuarioResponsavel(usuario);
        defineRisco(p);

        Projeto saved = repo.save(p);

        /* Adiciona membro ao projeto */
        Membro membro = new Membro();
        membro.setProjeto(saved);
        membro.setUsuario(usuario);
        membro.setDesCargo(Cargo.GERENTE);

        repoMembro.save(membro);

        return mapper.toDto(saved);
    }

    @Override
    public ProjetoDTO update(@NonNull Long id, @NonNull AtualizaProjetoDTO dto) {
        Projeto p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Não foi possível atualizar o projeto. Projeto não encontrado com o id: " + id));
        defineRisco(p);
        mapper.updateEntity(dto, p);

        Projeto saved = repo.save(p);

        return mapper.toDto(saved);
    }

    @Override
    public ProjetoDTO findById(@NonNull Long id) {
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não foi encontrado com o id: " + id));
    }

    @Override
    public ProjetoDTO atualizaStatusProjeto(@NonNull Long id) {
        Projeto projeto = repo.findById(id).get();

        if (projeto.getStAtual().equals(ProjectStatus.EM_ANDAMENTO)) {
            projeto.setStAtual(ProjectStatus.ENCERRADO);
        }

        if (projeto.getStAtual().equals(ProjectStatus.PLANEJADO)) {
            projeto.setStAtual(ProjectStatus.EM_ANDAMENTO);
        }

        if (projeto.getStAtual().equals(ProjectStatus.INICIADO)) {
            projeto.setStAtual(ProjectStatus.PLANEJADO);
        }

        if (projeto.getStAtual().equals(ProjectStatus.ANALISE_APROVADA)) {
            projeto.setStAtual(ProjectStatus.INICIADO);
        }

        if (projeto.getStAtual().equals(ProjectStatus.ANALISE_REALIZADA)) {
            projeto.setStAtual(ProjectStatus.ANALISE_APROVADA);
        }

        if (projeto.getStAtual().equals(ProjectStatus.EM_ANALISE)) {
            projeto.setStAtual(ProjectStatus.ANALISE_REALIZADA);
        }

        Projeto saved = repo.save(projeto);

        return mapper.toDto(saved);
    }

    @Override
    public ProjetoDTO cancelaProjeto(@NonNull Long id) {
        Projeto projeto = repo.findById(id).get();
        projeto.setStAtual(ProjectStatus.CANCELADO);

        LocalDate dataAtual = LocalDate.now();
        projeto.setDatFim(dataAtual);

        Projeto saved = repo.save(projeto);

        return mapper.toDto(saved);
    }

    @Override
    public void delete(@NonNull Long id) {
        Projeto projeto = repo.findById(id).get();

        if (projeto == null)
            throw new ResourceNotFoundException(
                    "Não foi possível excluir o projeto, o codigo informado é inválido: " + id);

        if (projeto.getStAtual().equals(ProjectStatus.EM_ANDAMENTO)
                || projeto.getStAtual().equals(ProjectStatus.PLANEJADO)
                || projeto.getStAtual().equals(ProjectStatus.INICIADO)) {
            throw new IllegalStateException(
                    "Não é possível excluir um projeto que está em andamento, planejado ou iniciado.");
        }

        repo.deleteById(id);
    }

    @Override
    public Page<ProjetoDTO> listAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    private void defineRisco(Projeto projeto) {
        LocalDate datePlus3Months = projeto.getDatInicio().plusMonths(3);
        LocalDate datePlus6Months = projeto.getDatInicio().plusMonths(6);

        if (projeto.getVlOrcamentoTotal().compareTo(new BigDecimal("100000")) <= 0
                && (projeto.getDatPrevisaoFim().isBefore(datePlus3Months)
                        || projeto.getDatPrevisaoFim().isEqual(datePlus3Months))) {

            projeto.setNivelRisco(repoRisco.findByDesNivelRisco("BAIXO")
                    .orElseThrow(() -> new ResourceNotFoundException("Nível de risco 'Baixo' não encontrado")));

        } else if ((projeto.getVlOrcamentoTotal().compareTo(new BigDecimal("100001")) >= 0
                && projeto.getVlOrcamentoTotal().compareTo(new BigDecimal("500000")) <= 0)
                || (projeto.getDatPrevisaoFim().isAfter(datePlus3Months)
                        && projeto.getDatPrevisaoFim().isBefore(datePlus6Months))) {

            projeto.setNivelRisco(repoRisco.findByDesNivelRisco("MEDIO")
                    .orElseThrow(() -> new ResourceNotFoundException("Nível de risco 'Médio' não encontrado")));

        } else if (projeto.getVlOrcamentoTotal().compareTo(new BigDecimal("500001")) >= 0
                || projeto.getDatPrevisaoFim().isAfter(datePlus6Months)) {

            projeto.setNivelRisco(repoRisco.findByDesNivelRisco("ALTO")
                    .orElseThrow(() -> new ResourceNotFoundException("Nível de risco 'Alto' não encontrado")));
        }
    }
}
