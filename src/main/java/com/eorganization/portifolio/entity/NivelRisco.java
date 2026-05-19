package com.eorganization.portifolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "tb_nivel_risco")
public class NivelRisco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_nivel_risco")
    private Long codNivelRisco;

    @Column(name = "des_nivel_risco", nullable = false)
    private String desNivelRisco;

    public NivelRisco(String desNivelRisco) {
        this.desNivelRisco = desNivelRisco;
    }
}
