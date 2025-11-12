package com.eorganization.portifolio.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eorganization.portifolio.dto.pessoa.CadastroPessoaDTO;
import com.eorganization.portifolio.dto.pessoa.PessoaDTO;
import com.eorganization.portifolio.entity.Pessoa;

@Component
public class PessoaMapper {
    private final ModelMapper mapper;

    public PessoaMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PessoaDTO toDto(Pessoa pessoa) {
        return mapper.map(pessoa, PessoaDTO.class);
    }

    public Pessoa toEntity(CadastroPessoaDTO dto) {
        if (dto == null)
            return null;

        Pessoa pessoa = mapper.map(dto, Pessoa.class);
        
        return pessoa;
    }
}
