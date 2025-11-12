package com.eorganization.portifolio.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eorganization.portifolio.dto.projeto.AtualizaProjetoDTO;
import com.eorganization.portifolio.dto.projeto.CadastroProjetoDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.entity.ProjectStatus;
import com.eorganization.portifolio.entity.Projeto;

@Component
public class ProjectMapper {
    private final ModelMapper mapper;

    public ProjectMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ProjetoDTO toDto(Projeto p) {
        if (p == null)
            return null;

        return mapper.map(p, ProjetoDTO.class);
    }

    public Projeto toEntity(CadastroProjetoDTO dto) {
        if (dto == null)
            return null;

        Projeto p = mapper.map(dto, Projeto.class);
        p.setStAtual(ProjectStatus.EM_ANALISE);

        return p;
    }

    public void updateEntity(AtualizaProjetoDTO dto, Projeto p) {
        if (dto.getNomProjeto() != null)
            p.setNomProjeto(dto.getNomProjeto());

        if (dto.getDesProjeto() != null)
            p.setDesProjeto(dto.getDesProjeto());

        if (dto.getDatInicio() != null)
            p.setDatInicio(dto.getDatInicio());

        if (dto.getDatPrevisaoFim() != null)
            p.setDatPrevisaoFim(dto.getDatPrevisaoFim());

        if (dto.getDatFim() != null)
            p.setDatFim(dto.getDatFim());

        if (dto.getVlOrcamentoTotal() != null)
            p.setVlOrcamentoTotal(dto.getVlOrcamentoTotal());

        if (dto.getStAtual() != null)
            p.setStAtual(dto.getStAtual());
    }
}
