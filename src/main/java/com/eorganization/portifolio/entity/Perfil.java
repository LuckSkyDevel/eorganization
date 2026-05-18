package com.eorganization.portifolio.entity;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "perfis", cascade=CascadeType.ALL)
    private Set<Usuario> usuarios;

    @Serial
    private static final long serialVersionUID = 1L;

}
