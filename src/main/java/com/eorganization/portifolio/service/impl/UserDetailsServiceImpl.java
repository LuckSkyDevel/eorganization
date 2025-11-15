package com.eorganization.portifolio.service.impl;

import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eorganization.portifolio.entity.Perfil;
import com.eorganization.portifolio.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = usuarioRepository.findByNomUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
            .username(usuario.getNomUsuario())
            .password(usuario.getDesSenha())
            .authorities(
                usuario.getPerfis().stream()
                .map(perfil -> perfil.getNomPerfil().toUpperCase())
                .toArray(String[]::new)
            )
            .build();
    }
}
