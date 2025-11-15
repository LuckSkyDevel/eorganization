package com.eorganization.portifolio.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eorganization.portifolio.entity.Perfil;
import com.eorganization.portifolio.entity.Pessoa;
import com.eorganization.portifolio.entity.Usuario;
import com.eorganization.portifolio.repository.PerfilRepository;
import com.eorganization.portifolio.repository.UsuarioRepository;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner init(UsuarioRepository userRepo, PerfilRepository perfilRepository, PasswordEncoder encoder) {
        return args -> {
            Pessoa pessoa = new Pessoa();
            pessoa.setNomPessoa("Jose Gustavo de Souza");
            pessoa.setNumCpf("123.456.789-00");
            pessoa.setNumTelefone("(11) 98765-4321");
            pessoa.setDatNascimento(LocalDate.parse("1990-05-15"));

            Set<Perfil> perfis = Set.of();
            if (perfilRepository.findAll().isEmpty()) {

                Perfil perfil = new Perfil();
                perfil.setNomPerfil("ADMIN");
                perfil.setDesPerfil("Administrador do sistema");
                perfis.add(perfil);
                
                perfil = new Perfil();
                perfil.setNomPerfil("GERENTE");
                perfil.setDesPerfil("Gerente de projetos");
                perfis.add(perfil);
                
                perfil = new Perfil();
                perfil.setNomPerfil("USUARIO");
                perfil.setDesPerfil("Usuário comum");
                perfis.add(perfil);
                
                perfil = new Perfil();
                perfil.setNomPerfil("FUNCIONARIO");
                perfil.setDesPerfil("Funcionario");
                perfis.add(perfil);

                perfilRepository.saveAll(perfis);
            }


            if (!userRepo.existsByNomUsuario("admin")) {
                Usuario u = new Usuario();
                u.setNomUsuario("admin");
                u.setDesSenha(encoder.encode("admin123"));
                u.setPessoa(pessoa);
                u.setPerfis(perfis);
                userRepo.save(u);
            }

            // if (!userRepo.existsByUsername("manager")) {
            // Usuario u = new Usuario();
            // u.setNomUsuario("manager");
            // u.setDesSenha(encoder.encode("managerpass"));
            // u.setPerfis(Set.of(Perfil.MANAGER));
            // userRepo.save(u);
            // }

            // if (!userRepo.existsByUsername("user")) {
            // Usuario u = new Usuario();
            // u.setNomUsuario("user");
            // u.setDesSenha(encoder.encode("userpass"));
            // u.setPerfis(Set.of(Perfil.USER));
            // userRepo.save(u);
            // }
        };
    }
}
