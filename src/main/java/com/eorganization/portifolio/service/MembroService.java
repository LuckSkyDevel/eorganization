package com.eorganization.portifolio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eorganization.portifolio.dto.pessoa.CreateMembroDTO;
import com.eorganization.portifolio.dto.pessoa.MembroDTO;
import com.eorganization.portifolio.entity.Membro;
import com.eorganization.portifolio.entity.Usuario;

public interface MembroService {
    CreateMembroDTO adicionaMembro(Membro membro);

    CreateMembroDTO adicionaMembroProjeto(CreateMembroDTO dto);

    MembroDTO findById(Long id);

    Page<MembroDTO> listAll(Pageable pageable);

    CreateMembroDTO verificaMembrosExistentesPorUsuario(Usuario usuario);
}
