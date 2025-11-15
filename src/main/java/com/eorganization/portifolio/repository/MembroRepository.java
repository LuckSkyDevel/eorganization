package com.eorganization.portifolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eorganization.portifolio.entity.Membro;

public interface MembroRepository extends JpaRepository<Membro, Long>, JpaSpecificationExecutor<Membro> {
    Optional<Membro> save(Long id);

    List<Membro> findAll();

    Optional<Membro> findById(Long id);

    List<Membro> findByProjetoCodProjeto(Long codProjeto);
}
