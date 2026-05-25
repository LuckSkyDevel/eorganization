package com.eorganization.portifolio.dto.pessoa;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PessoaDTO {
    private Long codPessoa;
    private String nomPessoa;
    private String numTelefone;
    private String numCpf;
    private LocalDate datNascimento;
    private Boolean isLiderEquipe;
    private Boolean stAtivo;
}
