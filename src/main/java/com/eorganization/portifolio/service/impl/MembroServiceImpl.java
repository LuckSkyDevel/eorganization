package com.eorganization.portifolio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eorganization.portifolio.dto.pessoa.CreateMembroDTO;
import com.eorganization.portifolio.dto.pessoa.MembroDTO;
import com.eorganization.portifolio.entity.Membro;
import com.eorganization.portifolio.entity.Usuario;
import com.eorganization.portifolio.exception.ResourceNotFoundException;
import com.eorganization.portifolio.mapper.MembroMapper;
import com.eorganization.portifolio.repository.MembroRepository;
import com.eorganization.portifolio.service.MembroService;

@Service
public class MembroServiceImpl implements MembroService {
    private final MembroRepository repository;
    private final MembroMapper mapper;

    public MembroServiceImpl(MembroRepository repository, MembroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CreateMembroDTO adicionaMembro(Membro membro) {
        if (membro == null) {
            throw new ResourceNotFoundException("Não foi possível adicionar o membro, parametros não encontrados!");
        }

        try {
            Membro saved = repository.save(membro);

            return mapper.toDto(saved);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível inserir o membro. Error:", e);
        }
    }

    @Override
    public CreateMembroDTO adicionaMembroProjeto(CreateMembroDTO dto) {
        if (dto == null) {
            throw new ResourceNotFoundException("Não foi possível adicionar o membro, parametros não encontrados!");
        }

        Membro membro = mapper.toEntity(dto);

        return adicionaMembro(membro);
    }

    @Override
    public MembroDTO findById(Long id) {
        return repository.findById(id).map(mapper::toMembroDto)
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado com o id: " + id));
    }

    @Override
    public Page<MembroDTO> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toMembroDto);
    }

    @Override
    public CreateMembroDTO verificaMembrosExistentesPorUsuario(Usuario usuario) {
        return repository.findByUsuario(usuario).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado para o usuário: " + usuario.getCodUsuario()));
    }
}
