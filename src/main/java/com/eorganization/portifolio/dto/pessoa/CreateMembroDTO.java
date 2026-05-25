package com.eorganization.portifolio.dto.pessoa;

import java.time.LocalDate;
import java.util.List;

import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.dto.usuario.UsuarioDTO;

import lombok.Data;

@Data
public class CreateMembroDTO {
    private Long codMembro;
    private List<ProjetoDTO> projetos;
    private UsuarioDTO usuario;
    private LocalDate datCriacao;
    private String desCargo;
    private Boolean stAtivo;
}
