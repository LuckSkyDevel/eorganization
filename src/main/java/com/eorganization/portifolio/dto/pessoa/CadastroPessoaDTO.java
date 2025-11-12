package com.eorganization.portifolio.dto.pessoa;

import java.time.LocalDate;
import java.util.List;

import com.eorganization.portifolio.dto.equipe.EquipeDTO;

import lombok.Data;

@Data
public class CadastroPessoaDTO {
    private String nomPessoa;
    private String numTelefone;
    private String numCpf;
    private LocalDate datNascimento;
    private List<EquipeDTO> equipes;
    private Boolean isLiderEquipe;
}
