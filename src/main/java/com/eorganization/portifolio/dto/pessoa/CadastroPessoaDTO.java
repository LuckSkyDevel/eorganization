package com.eorganization.portifolio.dto.pessoa;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CadastroPessoaDTO {
    private String nomPessoa;
    private String numTelefone;
    private String numCpf;
    private LocalDate datNascimento;
    private Boolean isLiderEquipe;
}
