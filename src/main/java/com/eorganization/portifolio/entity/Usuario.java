package com.eorganization.portifolio.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_usuario")
    private Long codUsuario;

    @Column(name="nom_usuario", nullable = false, unique = true)
    private String nomUsuario;

    @Column(name="des_senha", nullable = false)
    private String desSenha;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "cod_pessoa", nullable= false)
    private Pessoa pessoa;

    @ManyToMany
    @JoinTable(
        name = "tb_usuario_perfil",
        joinColumns = @JoinColumn(name = "cod_usuario"),
        inverseJoinColumns = @JoinColumn(name = "cod_perfil")
    )
    private Set<Perfil> perfis;

    public Usuario() {
    }

    public Usuario(String nomUsuario, String desSenha, Pessoa pessoa, Set<Perfil> perfis) {
        this.nomUsuario = nomUsuario;
        this.desSenha = desSenha;
        this.pessoa = pessoa;
        this.perfis = perfis;
    }

    // getters/setters
    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getDesSenha() {
        return desSenha;
    }

    public void setDesSenha(String desSenha) {
        this.desSenha = desSenha;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }
}
