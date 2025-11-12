package com.eorganization.portifolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eorganization.portifolio.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNomUsuario(String nomUsuario);

    boolean existsByNomUsuario(String nomUsuario);

    Usuario save(Usuario user);
}
