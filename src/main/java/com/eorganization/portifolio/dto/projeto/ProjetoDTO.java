package com.eorganization.portifolio.dto.projeto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.eorganization.portifolio.dto.NivelRiscoDTO;
import com.eorganization.portifolio.dto.pessoa.MembroDTO;
import com.eorganization.portifolio.dto.usuario.UsuarioDTO;
import com.eorganization.portifolio.entity.ProjectStatus;

import lombok.Data;

@Data
public class ProjetoDTO {
    private Long codProjeto;
    private UsuarioDTO usuarioResponsavel;
    private String nomProjeto;
    private String desProjeto;
    private LocalDate datInicio;
    private LocalDate datPrevisaoFim;
    private LocalDate datFim;
    private BigDecimal vlOrcamentoTotal;
    private NivelRiscoDTO nivelRisco;
    private List<MembroDTO> membros;
    private ProjectStatus stAtual;
}