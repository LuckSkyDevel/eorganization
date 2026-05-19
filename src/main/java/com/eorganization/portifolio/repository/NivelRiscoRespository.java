package com.eorganization.portifolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eorganization.portifolio.entity.NivelRisco;

public interface NivelRiscoRespository extends JpaRepository<NivelRisco, Long>, JpaSpecificationExecutor<NivelRisco> {
    Optional<NivelRisco> findByDesNivelRisco(String desNivelRisco);
}
