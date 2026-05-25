package com.eorganization.portifolio.dto.projeto;

import com.eorganization.portifolio.dto.usuario.UsuarioDTO;

import lombok.Data;

@Data
public class MembroProjetoDTO {
    private Long idProjeto;
    private UsuarioDTO usuario;    
}
