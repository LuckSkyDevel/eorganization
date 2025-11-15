package com.eorganization.portifolio.dto.projeto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
}
