package br.com.votos.mapper;

import br.com.votos.dto.AssociadoBasicoDTO;
import br.com.votos.dto.AssociadoCompletoDTO;
import br.com.votos.entidade.Associado;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    Associado toAssociado(AssociadoBasicoDTO associadoBasicoDTO);

    List<AssociadoCompletoDTO> toAssociadoCompletoDtoList(List<Associado> associados);
}
