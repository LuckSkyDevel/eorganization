package com.eorganization.portifolio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eorganization.portifolio.dto.projeto.AtualizaProjetoDTO;
import com.eorganization.portifolio.dto.projeto.CadastroProjetoDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;

public interface ProjetoService {
    ProjetoDTO create(CadastroProjetoDTO dto);

    ProjetoDTO update(Long id, AtualizaProjetoDTO dto);

    ProjetoDTO findById(Long id);

    ProjetoDTO atualizaStatusProjeto(Long id);

    void delete(Long id);

    Page<ProjetoDTO> listAll(Pageable pageable);
}
