package br.com.votos.mapper;

import br.com.votos.dto.AssociadoBasicoDTO;
import br.com.votos.dto.AssociadoCompletoDTO;
import br.com.votos.entidade.Associado;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface AssociadoMapper {

    Associado toAssociado(AssociadoBasicoDTO associadoBasicoDTO);

    List<AssociadoCompletoDTO> toAssociadoCompletoDtoList(List<Associado> associados);

    AssociadoCompletoDTO toAssociadoCompletoDto(Associado associado);
}
