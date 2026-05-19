package com.eorganization.portifolio.dto;

import com.eorganization.portifolio.dto.pessoa.PessoaDTO;

import lombok.Data;

@Data
public class UsuarioResponsanvelDTO {
    private Long codUsuario;
    private String nomUsuario;
    private PessoaDTO pessoa;
}
