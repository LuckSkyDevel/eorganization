package com.eorganization.portifolio.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eorganization.portifolio.dto.pessoa.CreateMembroDTO;
import com.eorganization.portifolio.dto.projeto.AtualizaProjetoDTO;
import com.eorganization.portifolio.dto.projeto.CadastroProjetoDTO;
import com.eorganization.portifolio.dto.projeto.MembroProjetoDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.entity.Cargo;
import com.eorganization.portifolio.entity.Membro;
import com.eorganization.portifolio.entity.Perfil;
import com.eorganization.portifolio.entity.Pessoa;
import com.eorganization.portifolio.entity.ProjectStatus;
import com.eorganization.portifolio.entity.Projeto;
import com.eorganization.portifolio.entity.Usuario;
import com.eorganization.portifolio.exception.ResourceNotFoundException;
import com.eorganization.portifolio.mapper.MembroMapper;
import com.eorganization.portifolio.mapper.ProjectMapper;
import com.eorganization.portifolio.repository.NivelRiscoRespository;
import com.eorganization.portifolio.repository.PerfilRepository;
import com.eorganization.portifolio.repository.ProjetoRepository;
import com.eorganization.portifolio.repository.UsuarioRepository;
import com.eorganization.portifolio.service.MembroService;
import com.eorganization.portifolio.service.ProjetoService;

import lombok.NonNull;

@Service
@Transactional
public class ProjetoServiceImpl implements ProjetoService {
    private final ProjetoRepository repo;
    private final NivelRiscoRespository repoRisco;
    private final PerfilRepository perfilRepository;
    private final UsuarioRepository repoUsuario;
    private final MembroService serviceMembro;
    private final ProjectMapper mapper;
    private final MembroMapper membroMapper;
    private final PasswordEncoder passwordEncoder;

    public ProjetoServiceImpl(ProjetoRepository repo, NivelRiscoRespository repoRisco,
            PerfilRepository perfilRepository, UsuarioRepository repoUsuario,
            MembroService serviceMembro, ProjectMapper mapper, MembroMapper membroMapper, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.repoRisco = repoRisco;
        this.perfilRepository = perfilRepository;
        this.repoUsuario = repoUsuario;
        this.serviceMembro = serviceMembro;
        this.mapper = mapper;
        this.membroMapper = membroMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<ProjetoDTO> listaTodosProjetos(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ProjetoDTO create(CadastroProjetoDTO dto) {
        Projeto p = mapper.toEntity(dto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        p.setUsuarioResponsavel(usuario);
        defineRisco(p);

        /* Adiciona membro ao projeto */
        Membro membro = new Membro();
        membro.setProjetos(Set.of(p));
        membro.setUsuario(usuario);
        membro.setDesCargo(Cargo.GERENTE);

        p.setMembros(Set.of(membro));

        Projeto saved = repo.save(p);

        serviceMembro.adicionaMembro(membro);

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
        Projeto projeto = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não foi encontrado com o id: " + id));

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
        Projeto projeto = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não foi encontrado com o id: " + id));
        projeto.setStAtual(ProjectStatus.CANCELADO);

        LocalDate dataAtual = LocalDate.now();
        projeto.setDatFim(dataAtual);

        Projeto saved = repo.save(projeto);

        return mapper.toDto(saved);
    }

    @Override
    public void delete(@NonNull Long id) {
        Projeto projeto = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não foi encontrado com o id: " + id));

        if (projeto.getStAtual().equals(ProjectStatus.EM_ANDAMENTO)
                || projeto.getStAtual().equals(ProjectStatus.PLANEJADO)
                || projeto.getStAtual().equals(ProjectStatus.INICIADO)) {
            throw new IllegalStateException(
                    "Não é possível excluir um projeto que está em andamento, planejado ou iniciado.");
        }

        repo.deleteById(id);
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
        } else {
            throw new IllegalStateException(
                    "Não foi possível definir o nível de risco do projeto. Verifique os critérios de orçamento e prazo.");
        }
    }

    @Override
    public List<ProjetoDTO> listaProjetoPorMembro(Long codMembro) {
        List<Projeto> projetos = repo.findByMembrosCodMembro(codMembro);
        return projetos.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProjetoDTO vinculaMembroAProjeto(MembroProjetoDTO dto) {
        if (dto == null)
            throw new ResourceNotFoundException("Não é possível vincular o membro ao projeto, membro não encontrado!");

        ProjetoDTO projeto = findById(dto.getIdProjeto());


        if (dto.getUsuario() != null && dto.getUsuario().getNomUsuario() != null) {
            Usuario usuario = repoUsuario.findByNomUsuario(dto.getUsuario().getNomUsuario()).orElse(null);

            if (usuario != null) {
                CreateMembroDTO membroExistente = serviceMembro.verificaMembrosExistentesPorUsuario(usuario);

                List<ProjetoDTO> projetosMembro = listaProjetoPorMembro(membroExistente.getCodMembro());

                if (projetosMembro != null && !projetosMembro.isEmpty()) {
                    boolean projetosAtivos = projetosMembro.stream()
                            .filter(proj -> !proj.getStAtual().equals(ProjectStatus.CANCELADO)
                                    && !proj.getStAtual().equals(ProjectStatus.ENCERRADO))
                            .collect(Collectors.toList()).size() >= 3;

                            if (projetosAtivos) {
                                throw new IllegalStateException(
                                        "O membro já está vinculado a 3 projetos ativos, não é possível vincular a mais projetos!");
                            }   
                }

                Membro membro = membroMapper.toEntity(membroExistente);
                Projeto projetoMapped = mapper.toEntity(projeto);
                membro.setProjetos(Set.of(projetoMapped));

                projetoMapped.setMembros(Set.of(membro));
                projetoMapped.setStAtual(projeto.getStAtual());
                var projetoSaved = repo.save(projetoMapped);

                return mapper.toDto(projetoSaved);
            } else {
                usuario = new Usuario();
                usuario.setNomUsuario(dto.getUsuario().getNomUsuario());
                usuario.setDesSenha(passwordEncoder.encode("123456"));

                Perfil perfil = new Perfil();
                perfil.setCodPerfil(3L);

                perfil = perfilRepository.findById(perfil.getCodPerfil())
                        .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado com o id informado!"));
                usuario.setPerfis(Set.of(perfil));

                Pessoa pessoa = new Pessoa();
                pessoa.setNomPessoa(dto.getUsuario().getPessoa().getNomPessoa());
                pessoa.setDatNascimento(dto.getUsuario().getPessoa().getDatNascimento());
                usuario.setPessoa(pessoa);

                Usuario savedUsuario = repoUsuario.save(usuario);

                Membro novoMembro = new Membro();
                novoMembro.setUsuario(savedUsuario);
                novoMembro.setDesCargo(Cargo.FUNCIONARIO);
                
                Projeto projetoFinal = mapper.toEntity(projeto);
                novoMembro.setProjetos(Set.of(projetoFinal));
                
                novoMembro = membroMapper.toEntity(serviceMembro.adicionaMembro(novoMembro));

                projetoFinal.getMembros().add(novoMembro);
                projetoFinal.setStAtual(projeto.getStAtual());

                projetoFinal = repo.save(projetoFinal);

                return mapper.toDto(projetoFinal);
            }
        } else {
            throw new ResourceNotFoundException(
                    "Não é possível vincular o membro ao projeto, pois membro não possui usuário!");
        }

    }
}
