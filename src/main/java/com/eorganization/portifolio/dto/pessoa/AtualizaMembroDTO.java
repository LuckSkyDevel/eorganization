package com.eorganization.portifolio.dto.pessoa;

import com.eorganization.portifolio.dto.projeto.ProjetoDTO;

import lombok.Data;

@Data
public class AtualizaMembroDTO {
    private Long codMembro;
    private ProjetoDTO projeto;
    private PessoaDTO pessoa;
    private Boolean stAtivo;
}
