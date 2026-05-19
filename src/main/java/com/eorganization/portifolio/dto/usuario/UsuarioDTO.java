package com.eorganization.portifolio.dto.usuario;

import com.eorganization.portifolio.dto.pessoa.PessoaDTO;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long codUsuario;
    private String nomUsuario;
    private PessoaDTO pessoa;
}
