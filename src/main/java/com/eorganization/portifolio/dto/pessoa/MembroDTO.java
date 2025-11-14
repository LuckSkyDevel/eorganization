package com.eorganization.portifolio.dto.pessoa;

import java.time.LocalDate;

import com.eorganization.portifolio.dto.projeto.ProjetoDTO;

import lombok.Data;

@Data
public class MembroDTO {
    private Long codMembro;
    private ProjetoDTO projeto;
    private PessoaDTO pessoa;
    private LocalDate datCriacao;
    private Boolean stAtivo;
}
