package com.eorganization.portifolio.mapper;

import org.modelmapper.ModelMapper;

import com.eorganization.portifolio.dto.pessoa.CreateMembroDTO;
import com.eorganization.portifolio.dto.pessoa.MembroDTO;
import com.eorganization.portifolio.dto.pessoa.PessoaDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.entity.Membro;
import com.eorganization.portifolio.entity.Pessoa;
import com.eorganization.portifolio.entity.Projeto;
import com.eorganization.portifolio.exception.ResourceNotFoundException;

public class MembroMapper {
    private final ModelMapper mapper;

    public MembroMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public MembroDTO toDto(Membro membro) {
        if (membro == null) 
            return null;

        MembroDTO membroDto = mapper.map(membro, MembroDTO.class);
        var projetoDto = mapper.map(membro.getProjeto(), ProjetoDTO.class);
        var pessoaDto = mapper.map(membro.getPessoa(), PessoaDTO.class);
 
        membroDto.setPessoa(pessoaDto);
        membroDto.setProjeto(projetoDto);

        return membroDto;
    }

    public Membro toEntity(CreateMembroDTO dto) {
        if (dto == null) 
            throw new ResourceNotFoundException("Wasn't convert nullable value!");

        Membro membro = mapper.map(dto, Membro.class);
        var projeto = mapper.map(dto.getProjeto(), Projeto.class);
        var pessoa = mapper.map(dto.getPessoa(), Pessoa.class);

        membro.setPessoa(pessoa);
        membro.setProjeto(projeto);

        return membro;
    }
}
