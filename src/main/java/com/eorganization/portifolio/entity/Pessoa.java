package com.eorganization.portifolio.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_pessoa")
    private Long codPessoa;

    @Column(name = "nom_pessoa")
    private String nomPessoa;

    @Column(name = "num_telefone")
    private String numTelefone;

    @Column(name = "num_cpf")
    private String numCpf;

    @Column(name = "dat_nascimento")
    private LocalDate datNascimento;

    @ManyToMany
    @JoinTable(name = "tb_pessoa_equipe", 
        joinColumns = @JoinColumn(name = "cod_pessoa"), 
        inverseJoinColumns = @JoinColumn(name = "cod_equipe"))
    private List<Equipe> equipes;

    @Column(name = "is_lider_equipe", nullable = false)
    private Boolean isLiderEquipe = false;

    @Column(name = "st_ativo", nullable = false)
    private Boolean stAtivo = true;

    // Getters and Setters
    public Long getCodPessoa() {
        return codPessoa;
    }

    public void setCodPessoa(Long codPessoa) {
        this.codPessoa = codPessoa;
    }

    public String getNomPessoa() {
        return nomPessoa;
    }

    public void setNomPessoa(String nomPessoa) {
        this.nomPessoa = nomPessoa;
    }

    public String getNumTelefone() {
        return numTelefone;
    }

    public void setNumTelefone(String numTelefone) {
        this.numTelefone = numTelefone;
    }

    public String getNumCpf() {
        return numCpf;
    }

    public void setNumCpf(String numCpf) {
        this.numCpf = numCpf;
    }

    public LocalDate getDatNascimento() {
        return datNascimento;
    }

    public void setDatNascimento(LocalDate datNascimento) {
        this.datNascimento = datNascimento;
    }

    public Boolean getStAtivo() {
        return stAtivo;
    }

    public void setStAtivo(Boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

}
