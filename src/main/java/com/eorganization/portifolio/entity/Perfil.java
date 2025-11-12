package com.eorganization.portifolio.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_perfil")
    private Long codPerfil;

    @Column(name="nom_perfil", nullable = false)
    private String nomPerfil;

    @Column(name="des_perfil", nullable = false)
    private String desPerfil;

    @Column(name="dat_criacao", nullable = false)
    private LocalDate datCriacao = LocalDate.now();

    @Column(name="st_ativo", nullable = false)
    private Boolean stAtivo = true;

    @ManyToMany(mappedBy = "perfis")
    private Set<Usuario> usuarios;

    // Getters and Setters
    public Long getCodPerfil() {
        return codPerfil;
    }

    public void setCodPerfil(Long codPerfil) {
        this.codPerfil = codPerfil;
    }

    public String getNomPerfil() {
        return nomPerfil;
    }

    public void setNomPerfil(String nomPerfil) {
        this.nomPerfil = nomPerfil;
    }

    public String getDesPerfil() {
        return desPerfil;
    }

    public void setDesPerfil(String desPerfil) {
        this.desPerfil = desPerfil;
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

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
