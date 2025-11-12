package com.eorganization.portifolio.payload;

import java.util.Set;

import com.eorganization.portifolio.dto.pessoa.CadastroPessoaDTO;

import lombok.Data;

@Data
public class RegisterRequest {

    private String nomUsuario;
    private String desSenha;
    private CadastroPessoaDTO pessoa;
    private Set<String> perfis;

    public Set<String> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<String> perfis) {
        this.perfis = perfis;
    }
}
