package br.com.votos.mapper;

import br.com.votos.dto.AssociadoBasicoDTO;
import br.com.votos.dto.AssociadoCompletoDTO;
import br.com.votos.entidade.Associado;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-26T16:17:04-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class AssociadoMapperImpl implements AssociadoMapper {

    @Override
    public Associado toAssociado(AssociadoBasicoDTO associadoBasicoDTO) {
        if ( associadoBasicoDTO == null ) {
            return null;
        }

        Associado.AssociadoBuilder associado = Associado.builder();

        associado.nome( associadoBasicoDTO.getNome() );
        associado.cpf( associadoBasicoDTO.getCpf() );

        return associado.build();
    }

    @Override
    public List<AssociadoCompletoDTO> toAssociadoCompletoDtoList(List<Associado> associados) {
        if ( associados == null ) {
            return null;
        }

        List<AssociadoCompletoDTO> list = new ArrayList<AssociadoCompletoDTO>( associados.size() );
        for ( Associado associado : associados ) {
            list.add( associadoToAssociadoCompletoDTO( associado ) );
        }

        return list;
    }

    protected AssociadoCompletoDTO associadoToAssociadoCompletoDTO(Associado associado) {
        if ( associado == null ) {
            return null;
        }

        AssociadoCompletoDTO.AssociadoCompletoDTOBuilder<?, ?> associadoCompletoDTO = AssociadoCompletoDTO.builder();

        associadoCompletoDTO.nome( associado.getNome() );
        associadoCompletoDTO.cpf( associado.getCpf() );
        associadoCompletoDTO.id( associado.getId() );

        return associadoCompletoDTO.build();
    }
}
