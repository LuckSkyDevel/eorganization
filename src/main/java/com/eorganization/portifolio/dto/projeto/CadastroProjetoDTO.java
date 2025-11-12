package com.eorganization.portifolio.dto.projeto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.eorganization.portifolio.dto.equipe.EquipeDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CadastroProjetoDTO {
    @NotBlank
    private String nomProjeto;
    private String desProjeto;
    private LocalDate datInicio;
    private LocalDate datPrevisaoFim;
    private LocalDate datFim;
    private BigDecimal vlOrcamentoTotal;
    private EquipeDTO equipe;
}
