package com.eorganization.portifolio.entity;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_usuario")
public class Usuario implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_usuario")
    private Long codUsuario;

    @Column(name="nom_usuario", nullable = false, unique = true)
    private String nomUsuario;

    @Column(name="des_senha", nullable = false)
    private String desSenha;

    @ManyToOne
    @JoinColumn(name = "cod_pessoa", nullable= false)
    private Pessoa pessoa;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_usuario_perfil",
        joinColumns = @JoinColumn(name = "cod_usuario"),
        inverseJoinColumns = @JoinColumn(name = "cod_perfil")
    )
    private Set<Perfil> perfis;

    @Serial
    private static final long serialVersionUID = 1L;

    public Usuario(String nomUsuario, String desSenha, Pessoa pessoa, Set<Perfil> perfis) {
        this.nomUsuario = nomUsuario;
        this.desSenha = desSenha;
        this.pessoa = pessoa;
        this.perfis = perfis;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis.stream()
            .map(p -> new SimpleGrantedAuthority("ROLE_" + p.getNomPerfil().toUpperCase()))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return desSenha; }

    @Override
    public String getUsername() { return nomUsuario; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
