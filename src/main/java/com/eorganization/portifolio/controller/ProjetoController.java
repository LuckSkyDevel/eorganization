package com.eorganization.portifolio.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eorganization.portifolio.dto.projeto.AtualizaProjetoDTO;
import com.eorganization.portifolio.dto.projeto.CadastroProjetoDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.service.ProjetoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {
    private final ProjetoService service;
    // private final MembroService membroService;

    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','USUARIO','FUNCIONARIO')")
    public ResponseEntity<Page<ProjetoDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','USUARIO','FUNCIONARIO')")
    public ResponseEntity<ProjetoDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<ProjetoDTO> create(@Valid @RequestBody CadastroProjetoDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // @PostMapping("/membro")
    // @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    // public ResponseEntity<MembroDTO> adicionaMembroProjeto(@Valid @RequestBody
    // CreateMembroDTO dto) {
    // return ResponseEntity.ok(membroService.adicionaMembroProjeto(dto));
    // }

    @PutMapping("/atualiza-status/:id")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','FUNCIONARIO')")
    public ResponseEntity<ProjetoDTO> atualizaStatus(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.atualizaStatusProjeto(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ProjetoDTO());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','FUNCIONARIO')")
    public ResponseEntity<ProjetoDTO> update(@PathVariable Long id, @RequestBody AtualizaProjetoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
