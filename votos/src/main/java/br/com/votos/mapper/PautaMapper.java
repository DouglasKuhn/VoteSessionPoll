package br.com.votos.mapper;

import br.com.votos.dto.PautaBasicoDTO;
import br.com.votos.dto.PautaCompletoDTO;
import br.com.votos.entidade.Pauta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface PautaMapper {

    Pauta toPauta(PautaBasicoDTO pautaDto);

    List<PautaCompletoDTO> toPautaCompletoDtoList(List<Pauta> pautas);

    PautaCompletoDTO toPautaCompletoDto(Pauta pauta);
}
