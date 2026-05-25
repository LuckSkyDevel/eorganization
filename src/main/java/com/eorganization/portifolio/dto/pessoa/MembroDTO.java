package com.eorganization.portifolio.dto.pessoa;

import com.eorganization.portifolio.dto.usuario.UsuarioDTO;

import lombok.Data;

@Data
public class MembroDTO {
    private Long codMembro;
    private UsuarioDTO usuario;
    private String desCargo;
}
