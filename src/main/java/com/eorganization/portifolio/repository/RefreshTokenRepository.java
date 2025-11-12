package com.eorganization.portifolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eorganization.portifolio.entity.RefreshToken;
import com.eorganization.portifolio.entity.Usuario;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(Usuario user);
}
