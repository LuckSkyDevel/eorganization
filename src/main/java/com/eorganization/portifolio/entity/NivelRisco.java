package com.eorganization.portifolio.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_nivel_risco")
public class NivelRisco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_nivel_risco")
    private Long codNivelRisco;

    @Column(name = "des_nivel_risco", nullable = false)
    private String desNivelRisco;

    @Column(name = "vl_orcamento", nullable = false)
    private BigDecimal vlOrcamento;

    // Getters and Setters
    public Long getCodNivelRisco() {
        return codNivelRisco;
    }

    public void setCodNivelRisco(Long codNivelRisco) {
        this.codNivelRisco = codNivelRisco;
    }

    public String getDesNivelRisco() {
        return desNivelRisco;
    }

    public void setDesNivelRisco(String desNivelRisco) {
        this.desNivelRisco = desNivelRisco;
    }

    public BigDecimal getVlOrcamento() {
        return vlOrcamento;
    }

    public void setVlOrcamento(BigDecimal vlOrcamento) {
        this.vlOrcamento = vlOrcamento;
    }
}
