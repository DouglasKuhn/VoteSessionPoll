package br.com.votos.mapper;

import br.com.votos.dto.PautaBasicoDTO;
import br.com.votos.dto.PautaCompletoDTO;
import br.com.votos.entidade.Pauta;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-27T14:17:37-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class PautaMapperImpl implements PautaMapper {

    @Override
    public Pauta toPauta(PautaBasicoDTO pautaDto) {
        if ( pautaDto == null ) {
            return null;
        }

        Pauta.PautaBuilder pauta = Pauta.builder();

        pauta.descricao( pautaDto.getDescricao() );
        pauta.dataInicio( pautaDto.getDataInicio() );
        pauta.dataFim( pautaDto.getDataFim() );

        return pauta.build();
    }

    @Override
    public List<PautaCompletoDTO> toPautaCompletoDtoList(List<Pauta> pautas) {
        if ( pautas == null ) {
            return null;
        }

        List<PautaCompletoDTO> list = new ArrayList<PautaCompletoDTO>( pautas.size() );
        for ( Pauta pauta : pautas ) {
            list.add( pautaToPautaCompletoDTO( pauta ) );
        }

        return list;
    }

    protected PautaCompletoDTO pautaToPautaCompletoDTO(Pauta pauta) {
        if ( pauta == null ) {
            return null;
        }

        PautaCompletoDTO.PautaCompletoDTOBuilder<?, ?> pautaCompletoDTO = PautaCompletoDTO.builder();

        pautaCompletoDTO.descricao( pauta.getDescricao() );
        pautaCompletoDTO.dataInicio( pauta.getDataInicio() );
        pautaCompletoDTO.dataFim( pauta.getDataFim() );
        pautaCompletoDTO.id( pauta.getId() );

        return pautaCompletoDTO.build();
    }
}
