package com.eorganization.portifolio.dto.pessoa;

import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.dto.usuario.UsuarioDTO;

import lombok.Data;

@Data
public class CreateMembroDTO {
    private ProjetoDTO projeto;
    private UsuarioDTO usuario;
}
