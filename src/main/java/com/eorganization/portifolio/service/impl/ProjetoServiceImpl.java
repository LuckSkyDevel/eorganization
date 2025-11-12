package com.eorganization.portifolio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eorganization.portifolio.dto.projeto.AtualizaProjetoDTO;
import com.eorganization.portifolio.dto.projeto.CadastroProjetoDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.entity.Projeto;
import com.eorganization.portifolio.exception.ResourceNotFoundException;
import com.eorganization.portifolio.mapper.ProjectMapper;
import com.eorganization.portifolio.repository.ProjetoRepository;
import com.eorganization.portifolio.service.ProjetoService;

import lombok.NonNull;

@Service
@Transactional
public class ProjetoServiceImpl implements ProjetoService {
    private final ProjetoRepository repo;
    private final ProjectMapper mapper;

    public ProjetoServiceImpl(ProjetoRepository repo, ProjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ProjetoDTO create(CadastroProjetoDTO dto) {
        Projeto p = mapper.toEntity(dto);
        Projeto saved = repo.save(p);

        return mapper.toDto(saved);
    }

    @Override
    public ProjetoDTO update(@NonNull Long id, @NonNull AtualizaProjetoDTO dto) {
        Projeto p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wasn't possible to update project. Project not found with id: " + id));
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
    public void delete(@NonNull Long id) {
        if (!repo.existsById(id))
            throw new ResourceNotFoundException("Wasn't possible to delete project. Project not found with id: " + id);
        repo.deleteById(id);
    }

    @Override
    public Page<ProjetoDTO> listAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }
}
