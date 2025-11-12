package com.eorganization.portifolio.dto.pessoa;

import java.util.List;

import com.eorganization.portifolio.dto.equipe.EquipeDTO;

import lombok.Data;

@Data
public class PessoaDTO {
    private Long codPessoa;
    private String nomPessoa;
    private String numTelefone;
    private String numCpf;
    private String datNascimento;
    private List<EquipeDTO> equipes;
    private Boolean isLiderEquipe;
    private Boolean stAtivo;
}
