package com.eorganization.portifolio.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_equipe")
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_equipe")
    private Long codEquipe;

    @Column(name="nom_equipe", nullable = false)
    private String nomEquipe;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "equipes")
    private List<Pessoa> membrosEquipe;

    @Column(name="st_ativo", nullable = false)
    private Boolean stAtivo = true;
}
