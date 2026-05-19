package com.eorganization.portifolio.dto.pessoa;

import java.time.LocalDate;

import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.dto.usuario.UsuarioDTO;

import lombok.Data;

@Data
public class MembroDTO {
    private Long codMembro;
    private ProjetoDTO projeto;
    private UsuarioDTO usuario;
    private LocalDate datCriacao;
    private Boolean stAtivo;
}
