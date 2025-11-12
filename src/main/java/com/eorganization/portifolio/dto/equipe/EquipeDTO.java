package com.eorganization.portifolio.dto.equipe;

import java.util.List;

import com.eorganization.portifolio.dto.pessoa.PessoaDTO;

import lombok.Data;

@Data
public class EquipeDTO {
    private Long codigoEquipe;
    private String nomeEquipe;
    private List<PessoaDTO> membrosEquipe;

}
