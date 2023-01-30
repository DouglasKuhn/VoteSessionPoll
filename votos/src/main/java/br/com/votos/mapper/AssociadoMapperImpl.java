package br.com.votos.mapper;

import br.com.votos.dto.AssociadoBasicoDTO;
import br.com.votos.dto.AssociadoCompletoDTO;
import br.com.votos.dto.AssociadoCompletoDTO.AssociadoCompletoDTOBuilder;
import br.com.votos.entidade.Associado;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public AssociadoCompletoDTO toAssociadoCompletoDto(Associado associado) {
        if ( associado == null ) {
            return null;
        }

        AssociadoCompletoDTOBuilder<?, ?> associadoCompletoDTO = AssociadoCompletoDTO.builder();

        associadoCompletoDTO.nome( associado.getNome() );
        associadoCompletoDTO.cpf( associado.getCpf() );
        associadoCompletoDTO.id( associado.getId() );

        return associadoCompletoDTO.build();
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
