package com.eorganization.portifolio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eorganization.portifolio.dto.pessoa.AtualizaMembroDTO;
import com.eorganization.portifolio.dto.pessoa.CreateMembroDTO;
import com.eorganization.portifolio.dto.pessoa.MembroDTO;
import com.eorganization.portifolio.entity.Membro;
import com.eorganization.portifolio.exception.ResourceNotFoundException;
import com.eorganization.portifolio.mapper.MembroMapper;
import com.eorganization.portifolio.repository.MembroRepository;
import com.eorganization.portifolio.service.MembroService;

public class MembroServiceImpl implements MembroService {
    private final MembroRepository repository;
    private final MembroMapper mapper;

    public MembroServiceImpl(MembroRepository repository, MembroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public MembroDTO adicionaMembroProjeto(CreateMembroDTO dto) {
        if (dto == null) {
            throw new ResourceNotFoundException("Wasn't add member in project, but params not presents!");
        }

        var membrosProjeto = repository.findByProjetoCodProjeto(dto.getProjeto().getCodProjeto());
        if (!membrosProjeto.isEmpty() && membrosProjeto.size() == 10) {
            throw new RuntimeException("Only is possible add 10 members in project!");
        }

         
        try {
            Membro membro = mapper.toEntity(dto);
            Membro saved = repository.save(membro);
    
            return mapper.toDto(saved);
        } catch(Exception e) {
            throw new RuntimeException("Wasn't insert project. Error:", e);
        }
    }

    @Override
    public MembroDTO atualizaMembroProjeto(Long id, AtualizaMembroDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public MembroDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    @Override
    public void removeMembroProjeto(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeMembroProjeto'");
    }

    @Override
    public Page<MembroDTO> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }
    
}
