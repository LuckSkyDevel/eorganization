package com.eorganization.portifolio.dto.projeto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.eorganization.portifolio.dto.NivelRiscoDTO;
import com.eorganization.portifolio.entity.ProjectStatus;

import lombok.Data;

@Data
public class AtualizaProjetoDTO {
    private String nomProjeto;
    private String desProjeto;
    private LocalDate datInicio;
    private LocalDate datPrevisaoFim;
    private LocalDate datFim;
    private BigDecimal vlOrcamentoTotal;
    private NivelRiscoDTO nivelRisco;
    private ProjectStatus stAtual;
}