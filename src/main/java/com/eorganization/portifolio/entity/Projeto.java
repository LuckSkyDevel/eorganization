package com.eorganization.portifolio.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_projetos")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_projeto")
    private Long codProjeto;

    @Column(name = "nom_projeto", nullable = false)
    private String nomProjeto;

    @Column(name = "ds_projeto", nullable = false)
    private String desProjeto;

    @Column(name = "dat_inicio")
    private LocalDate datInicio;

    @Column(name = "dat_previsao_fim")
    private LocalDate datPrevisaoFim;

    @Column(name = "dt_fim")
    private LocalDate datFim;

    @Column(name = "vl_orcamento_total", nullable = false)
    private BigDecimal vlOrcamentoTotal;

    @OneToOne
    @JoinColumn(name = "cod_equipe", nullable = false)
    private Equipe equipe;

    @OneToOne
    @JoinColumn(name = "cod_nivel_risco", nullable = false)
    private NivelRisco nivelRisco;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_atual", nullable = false)
    private ProjectStatus stAtual;

    public Projeto() {
    }

    public Projeto(Long codProjeto, String nomProjeto, String dsProjeto, LocalDate datInicio, LocalDate datPrevisaoFim,
            LocalDate datFim, BigDecimal vlOrcamentoTotal, Equipe equipe, NivelRisco nivelRisco,
            ProjectStatus stAtual) {
        this.codProjeto = codProjeto;
        this.nomProjeto = nomProjeto;
        this.desProjeto = dsProjeto;
        this.datInicio = datInicio;
        this.datPrevisaoFim = datPrevisaoFim;
        this.datFim = datFim;
        this.vlOrcamentoTotal = vlOrcamentoTotal;
        this.equipe = equipe;
        this.nivelRisco = nivelRisco;
        this.stAtual = stAtual;
    }

    // Getters and Setters
    public Long getCodProjeto() {
        return codProjeto;
    }

    public void setCodProjeto(Long codProjeto) {
        this.codProjeto = codProjeto;
    }

    public String getNomProjeto() {
        return nomProjeto;
    }

    public void setNomProjeto(String nomProjeto) {
        this.nomProjeto = nomProjeto;
    }

    public String getDesProjeto() {
        return desProjeto;
    }

    public void setDesProjeto(String dsProjeto) {
        this.desProjeto = dsProjeto;
    }

    public LocalDate getDatInicio() {
        return datInicio;
    }

    public void setDatInicio(LocalDate datInicio) {
        this.datInicio = datInicio;
    }

    public LocalDate getDatPrevisaoFim() {
        return datPrevisaoFim;
    }

    public void setDatPrevisaoFim(LocalDate datPrevisaoFim) {
        this.datPrevisaoFim = datPrevisaoFim;
    }

    public LocalDate getDatFim() {
        return datFim;
    }

    public void setDatFim(LocalDate datFim) {
        this.datFim = datFim;
    }

    public BigDecimal getVlOrcamentoTotal() {
        return vlOrcamentoTotal;
    }

    public void setVlOrcamentoTotal(BigDecimal vlOrcamentoTotal) {
        this.vlOrcamentoTotal = vlOrcamentoTotal;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public NivelRisco getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(NivelRisco nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public ProjectStatus getStAtual() {
        return stAtual;
    }

    public void setStAtual(ProjectStatus stAtual) {
        this.stAtual = stAtual;
    }
}
