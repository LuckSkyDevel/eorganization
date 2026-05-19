package com.eorganization.portifolio.mapper;

import org.modelmapper.ModelMapper;

import com.eorganization.portifolio.dto.pessoa.CreateMembroDTO;
import com.eorganization.portifolio.dto.pessoa.MembroDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.dto.usuario.UsuarioDTO;
import com.eorganization.portifolio.entity.Membro;
import com.eorganization.portifolio.entity.Projeto;
import com.eorganization.portifolio.entity.Usuario;
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
        var usuarioDto = mapper.map(membro.getUsuario(), UsuarioDTO.class);
 
        membroDto.setUsuario(usuarioDto);
        membroDto.setProjeto(projetoDto);

        return membroDto;
    }

    public Membro toEntity(CreateMembroDTO dto) {
        if (dto == null) 
            throw new ResourceNotFoundException("Não é possível converter valor nulo!");

        Membro membro = mapper.map(dto, Membro.class);
        var projeto = mapper.map(dto.getProjeto(), Projeto.class);
        var usuario = mapper.map(dto.getUsuario(), Usuario.class);

        membro.setUsuario(usuario);
        membro.setProjeto(projeto);

        return membro;
    }
}
