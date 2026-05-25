package com.eorganization.portifolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eorganization.portifolio.entity.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>, JpaSpecificationExecutor<Projeto> {
    List<Projeto> findByMembrosCodMembro(Long codMembro);
}
