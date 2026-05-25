package com.eorganization.portifolio.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_membro")
public class Membro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_membro")
    private Long codMembro;

    @ManyToMany(mappedBy = "membros")
    private Set<Projeto> projetos;

    @ManyToOne
    @JoinColumn(name = "cod_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "dat_criacao")
    private LocalDate datCriacao = LocalDate.now();
    
    @Column(name = "st_ativo")
    private Boolean stAtivo = true;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "des_cargo", nullable = false)
    private Cargo desCargo;

}

