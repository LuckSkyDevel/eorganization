package com.eorganization.portifolio.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eorganization.portifolio.entity.Perfil;
import com.eorganization.portifolio.entity.Pessoa;
import com.eorganization.portifolio.entity.RefreshToken;
import com.eorganization.portifolio.entity.Usuario;
import com.eorganization.portifolio.mapper.PessoaMapper;
import com.eorganization.portifolio.payload.AuthRequest;
import com.eorganization.portifolio.payload.AuthResponse;
import com.eorganization.portifolio.payload.RegisterRequest;
import com.eorganization.portifolio.repository.PerfilRepository;
import com.eorganization.portifolio.repository.RefreshTokenRepository;
import com.eorganization.portifolio.repository.UsuarioRepository;
import com.eorganization.portifolio.security.JwtService;
import com.eorganization.portifolio.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final UsuarioRepository userRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PessoaMapper pessoaMapper;

    public AuthServiceImpl(AuthenticationManager authManager, UsuarioRepository userRepository,
            PasswordEncoder passwordEncoder, JwtService jwtService,
            RefreshTokenRepository refreshTokenRepository, PerfilRepository perfilRepository,
            PessoaMapper pessoaMapper) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.perfilRepository = perfilRepository;
        this.pessoaMapper = pessoaMapper;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            // Authenticate user credentials
            authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()));

            // Fetch user by username (AuthRequest.username maps to Usuario.nomUsuario)
            var usuario = userRepository.findByNomUsuario(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found: " + request.getUsername()));

            // Extract and validate profiles
            if (usuario.getPerfis() == null || usuario.getPerfis().isEmpty()) {
                throw new RuntimeException("User has no assigned profiles");
            }

            var perfis = usuario.getPerfis().stream().map(Perfil::getNomPerfil).collect(Collectors.toList());

            // Generate tokens
            var access = jwtService.generateAccessToken(usuario.getNomUsuario(), perfis);
            var refresh = jwtService.generateRefreshToken(usuario.getNomUsuario());

            // Persist refresh token (14 days expiry)
            RefreshToken rToken = new RefreshToken();
            rToken.setToken(refresh);
            rToken.setExpiryDate(Instant.now().plusSeconds(1209600)); // 14 days
            rToken.setUser(usuario);
            refreshTokenRepository.save(rToken);

            return new AuthResponse(access, refresh);

        } catch (Exception exception) {
            throw new RuntimeException("Error during login", exception);
        }
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        var opt = refreshTokenRepository.findByToken(refreshToken);
        if (opt.isEmpty()) {
            throw new RuntimeException("Invalid refresh token");
        }

        if (opt.get().getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired, please login again!");
        }

        String nomUsuario = jwtService.extractUsername(refreshToken);
        var usuario = userRepository.findByNomUsuario(nomUsuario).orElseThrow();
        var perfis = usuario.getPerfis().stream().map(Perfil::getNomPerfil).collect(Collectors.toList());
        String access = jwtService.generateAccessToken(nomUsuario, perfis);

        return new AuthResponse(access, refreshToken);
    }

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.existsByNomUsuario(request.getNomUsuario())) {
            throw new RuntimeException("Wasn't possible to register. Username already taken!");
        }

        if (perfilRepository.findAll().isEmpty()) {
            throw new RuntimeException("Wasn't possible to register. No profiles found!");
        }

        List<Perfil> perfis = perfilRepository.findAll();

        Usuario u = new Usuario();
        u.setNomUsuario(request.getNomUsuario());
        u.setDesSenha(passwordEncoder.encode(request.getDesSenha()));

        var profiles = request.getPerfis() == null ? Set.of(perfis.get(0))
                : request.getPerfis().stream()
                        .map(s -> perfilRepository.findByNomPerfil(s)
                                .orElseThrow(() -> new RuntimeException("Profile not found")))
                        .collect(Collectors.toSet());
        u.setPerfis(profiles);

        Pessoa pessoa = pessoaMapper.toEntity(request.getPessoa());
        u.setPessoa(pessoa);

        userRepository.save(u);
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).ifPresent(token -> refreshTokenRepository.delete(token));
    }
}
