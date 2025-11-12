package com.eorganization.portifolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eorganization.portifolio.entity.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>, JpaSpecificationExecutor<Perfil> {

    List<Perfil> findAll();

    Optional<Perfil> findById(Long id);

    Optional<Perfil> findByNomPerfil(String nomPerfil);

    Perfil save(Perfil perfil);

}
