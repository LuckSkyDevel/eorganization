package com.eorganization.portifolio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eorganization.portifolio.dto.pessoa.AtualizaMembroDTO;
import com.eorganization.portifolio.dto.pessoa.CreateMembroDTO;
import com.eorganization.portifolio.dto.pessoa.MembroDTO;


public interface MembroService {
    MembroDTO adicionaMembroProjeto(CreateMembroDTO dto);

    MembroDTO atualizaMembroProjeto(Long id, AtualizaMembroDTO dto); 

    MembroDTO findById(Long id);

    void removeMembroProjeto(Long id);

    Page<MembroDTO> listAll(Pageable pageable);

}
