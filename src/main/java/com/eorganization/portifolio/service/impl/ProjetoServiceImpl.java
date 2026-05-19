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
import com.eorganization.portifolio.entity.ProjectStatus;
import com.eorganization.portifolio.entity.Projeto;
import com.eorganization.portifolio.entity.Usuario;
import com.eorganization.portifolio.exception.ResourceNotFoundException;
import com.eorganization.portifolio.mapper.ProjectMapper;
import com.eorganization.portifolio.repository.NivelRiscoRespository;
import com.eorganization.portifolio.repository.ProjetoRepository;
import com.eorganization.portifolio.service.ProjetoService;

import lombok.NonNull;

@Service
@Transactional
public class ProjetoServiceImpl implements ProjetoService {
    private final ProjetoRepository repo;
    private final NivelRiscoRespository repoRisco;
    private final ProjectMapper mapper;

    public ProjetoServiceImpl(ProjetoRepository repo, NivelRiscoRespository repoRisco, ProjectMapper mapper) {
        this.repo = repo;
        this.repoRisco = repoRisco;
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

        return mapper.toDto(saved);
    }

    @Override
    public ProjetoDTO update(@NonNull Long id, @NonNull AtualizaProjetoDTO dto) {
        Projeto p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Wasn't possible to update project. Project not found with id: " + id));
        defineRisco(p);
        mapper.updateEntity(dto, p);

        Projeto saved = repo.save(p);

        return mapper.toDto(saved);
    }

    @Override
    public ProjetoDTO findById(@NonNull Long id) {
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
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

        return mapper.toDto(projeto);
    }

    @Override
    public void delete(@NonNull Long id) {
        if (!repo.existsById(id))
            throw new ResourceNotFoundException("Wasn't possible to delete project. Project not found with id: " + id);
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
