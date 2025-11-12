package com.eorganization.portifolio.dto.projeto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.eorganization.portifolio.dto.NivelRiscoDTO;
import com.eorganization.portifolio.dto.equipe.EquipeDTO;
import com.eorganization.portifolio.entity.ProjectStatus;

@Data
public class ProjetoDTO {
    private Long codProjeto;
    private String nomProjeto;
    private String desProjeto;
    private LocalDate datInicio;
    private LocalDate datPrevisaoFim;
    private LocalDate datFim;
    private BigDecimal vlOrcamentoTotal;
    private EquipeDTO equipe;
    private NivelRiscoDTO nivelRisco;
    private ProjectStatus stAtual;
}