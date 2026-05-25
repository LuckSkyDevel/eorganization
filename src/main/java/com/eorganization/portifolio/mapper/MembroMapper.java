package com.eorganization.portifolio.mapper;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eorganization.portifolio.dto.pessoa.CreateMembroDTO;
import com.eorganization.portifolio.dto.pessoa.MembroDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.dto.usuario.UsuarioDTO;
import com.eorganization.portifolio.entity.Membro;
import com.eorganization.portifolio.entity.Projeto;
import com.eorganization.portifolio.entity.Usuario;
import com.eorganization.portifolio.exception.ResourceNotFoundException;

@Component
public class MembroMapper {
    private final ModelMapper mapper;

    public MembroMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CreateMembroDTO toDto(Membro membro) {
        if (membro == null)
            return null;

        CreateMembroDTO membroDto = mapper.map(membro, CreateMembroDTO.class);
        var projetoDto = mapper.map(membro.getProjetos(), ProjetoDTO.class);
        var usuarioDto = mapper.map(membro.getUsuario(), UsuarioDTO.class);

        membroDto.setUsuario(usuarioDto);
        membroDto.setProjetos(List.of(projetoDto));

        return membroDto;
    }

    public MembroDTO toMembroDto(Membro membro) {
        if (membro == null)
            return null;

        MembroDTO membroDto = mapper.map(membro, MembroDTO.class);
        var usuarioDto = mapper.map(membro.getUsuario(), UsuarioDTO.class);

        membroDto.setUsuario(usuarioDto);

        return membroDto;
    }

    public Membro toEntity(CreateMembroDTO dto) {
        if (dto == null)
            throw new ResourceNotFoundException("Não é possível converter valor nulo!");

        Membro membro = mapper.map(dto, Membro.class);
        var projetos = mapper.map(dto.getProjetos(), Projeto.class);
        var usuario = mapper.map(dto.getUsuario(), Usuario.class);

        membro.setUsuario(usuario);
        membro.setProjetos(Set.of(projetos));

        return membro;
    }
}
