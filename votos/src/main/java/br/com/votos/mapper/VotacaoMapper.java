package br.com.votos.mapper;

import br.com.votos.dto.VotacaoDTO;
import br.com.votos.dto.VotoDTO;
import br.com.votos.entidade.Votacao;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VotacaoMapper {

    @Mapping(source = "idAssociado", target = "votoId.associado.id")
    @Mapping(source = "idPauta", target = "votoId.pauta.id")
    Votacao toVotacao(VotoDTO votoDTO);

    @Mapping(source = "votoId.associado.nome", target = "associado.nome")
    @Mapping(source = "votoId.associado.cpf", target = "associado.cpf")
    @Mapping(source = "votoId.pauta.descricao", target = "pauta.descricao")
    @Mapping(source = "votoId.pauta.dtInicio", target = "pauta.dataInicio")
    @Mapping(source = "votoId.pauta.dtFim", target = "pauta.dataFim")
    VotacaoDTO toVotacaoDto(Votacao votacao);

    @IterableMapping(elementTargetType = VotacaoDTO.class)
    List<VotacaoDTO> toVotacaoDtoList(List<Votacao> votacoes);
}
