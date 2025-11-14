package com.eorganization.portifolio.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_membro")
public class Membro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_membro")
    private Long codMembro;

    @ManyToOne
    @JoinColumn(name = "cod_projeto")
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "cod_pessoa")
    private Pessoa pessoa;

    @Column(name = "dat_criacao")
    private LocalDate datCriacao = LocalDate.now();

    @Column(name = "st_ativo")
    private Boolean stAtivo = true;

    
    public Long getCodMembro() {
        return codMembro;
    }

    public void setCodMembro(Long codMembro) {
        this.codMembro = codMembro;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public LocalDate getDatCriacao() {
        return datCriacao;
    }

    public void setDatCriacao(LocalDate datCriacao) {
        this.datCriacao = datCriacao;
    }

    public Boolean getStAtivo() {
        return stAtivo;
    }

    public void setStAtivo(Boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

}

