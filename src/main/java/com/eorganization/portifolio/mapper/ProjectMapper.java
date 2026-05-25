package com.eorganization.portifolio.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eorganization.portifolio.dto.NivelRiscoDTO;
import com.eorganization.portifolio.dto.pessoa.MembroDTO;
import com.eorganization.portifolio.dto.projeto.AtualizaProjetoDTO;
import com.eorganization.portifolio.dto.projeto.CadastroProjetoDTO;
import com.eorganization.portifolio.dto.projeto.ProjetoDTO;
import com.eorganization.portifolio.dto.usuario.UsuarioDTO;
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

        var dto = new ProjetoDTO();
        dto.setCodProjeto(p.getCodProjeto());
        dto.setNomProjeto(p.getNomProjeto());
        dto.setDesProjeto(p.getDesProjeto());
        dto.setDatInicio(p.getDatInicio());
        dto.setDatPrevisaoFim(p.getDatPrevisaoFim());
        dto.setDatFim(p.getDatFim());
        dto.setVlOrcamentoTotal(p.getVlOrcamentoTotal());
        dto.setStAtual(p.getStAtual());

        var usuarioResponsavel = mapper.map(p.getUsuarioResponsavel(), UsuarioDTO.class);
        dto.setUsuarioResponsavel(usuarioResponsavel);

        var nivelRisco = mapper.map(p.getNivelRisco(), NivelRiscoDTO.class);
        dto.setNivelRisco(nivelRisco);

        var membros = p.getMembros().stream()
                .map(m -> mapper.map(m, MembroDTO.class))
                .toList();
        dto.setMembros(membros);

        return dto;
    }

    public Projeto toEntity(ProjetoDTO dto, CadastroProjetoDTO cDto) {
        if (dto == null) {
            if (cDto == null) {
                return null;
            } else {
                return toEntity(cDto);
            }
        }
            
        return toEntity(dto);
    }

    public Projeto toEntity(CadastroProjetoDTO dto) {
        Projeto p = mapper.map(dto, Projeto.class);
        p.setStAtual(ProjectStatus.EM_ANALISE);

        return p;
    }

    public Projeto toEntity(ProjetoDTO dto) {
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
