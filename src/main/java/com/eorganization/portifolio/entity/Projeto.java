package com.eorganization.portifolio.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_projetos")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_projeto")
    private Long codProjeto;

    @ManyToOne
    @JoinColumn(name = "cod_usuario_responsavel",referencedColumnName="cod_usuario", nullable = false)
    private Usuario usuarioResponsavel;

    @Column(name = "nom_projeto", nullable = false)
    private String nomProjeto;

    @Column(name = "ds_projeto", nullable = false)
    private String desProjeto;

    @Column(name = "dat_inicio", nullable = false)
    private LocalDate datInicio;

    @Column(name = "dat_previsao_fim", nullable=false)
    private LocalDate datPrevisaoFim;

    @Column(name = "dat_fim", nullable = true)
    private LocalDate datFim;

    @Column(name = "vl_orcamento_total", nullable = false)
    private BigDecimal vlOrcamentoTotal;

    @ManyToOne
    @JoinColumn(name = "cod_nivel_risco", nullable = false)
    private NivelRisco nivelRisco;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_atual", nullable = false)
    private ProjectStatus stAtual;

}
