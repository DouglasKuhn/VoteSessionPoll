package br.com.votos.mapper;

import br.com.votos.dto.AssociadoBasicoDTO;
import br.com.votos.dto.PautaBasicoDTO;
import br.com.votos.dto.VotacaoDTO;
import br.com.votos.dto.VotoDTO;
import br.com.votos.dto.enums.VotoEnumDTO;
import br.com.votos.entidade.Associado;
import br.com.votos.entidade.Pauta;
import br.com.votos.entidade.Votacao;
import br.com.votos.entidade.VotoId;
import br.com.votos.entidade.enums.VotoEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class VotacaoMapperImpl implements VotacaoMapper {

    @Override
    public Votacao toVotacao(VotoDTO votoDTO) {
        if ( votoDTO == null ) {
            return null;
        }

        Votacao.VotacaoBuilder votacao = Votacao.builder();

        votacao.votoId( votoDTOToVotoId( votoDTO ) );
        votacao.voto( votoDTO.getVoto() );

        return votacao.build();
    }

    @Override
    public VotacaoDTO toVotacaoDto(Votacao votacao) {
        if ( votacao == null ) {
            return null;
        }

        VotacaoDTO.VotacaoDTOBuilder<?, ?> votacaoDTO = VotacaoDTO.builder();

        votacaoDTO.associado( votoIdToAssociadoBasicoDTO( votacao.getVotoId() ) );
        votacaoDTO.pauta( votoIdToPautaBasicoDTO( votacao.getVotoId() ) );
        votacaoDTO.voto( votoEnumToVotoEnumDTO( votacao.getVoto() ) );

        return votacaoDTO.build();
    }

    @Override
    public List<VotacaoDTO> toVotacaoDtoList(List<Votacao> votacoes) {
        if ( votacoes == null ) {
            return null;
        }

        List<VotacaoDTO> list = new ArrayList<VotacaoDTO>( votacoes.size() );
        for ( Votacao votacao : votacoes ) {
            list.add( toVotacaoDto( votacao ) );
        }

        return list;
    }

    protected Associado votoDTOToAssociado(VotoDTO votoDTO) {
        if ( votoDTO == null ) {
            return null;
        }

        Associado.AssociadoBuilder associado = Associado.builder();

        associado.id( votoDTO.getIdAssociado() );

        return associado.build();
    }

    protected Pauta votoDTOToPauta(VotoDTO votoDTO) {
        if ( votoDTO == null ) {
            return null;
        }

        Pauta.PautaBuilder pauta = Pauta.builder();

        pauta.id( votoDTO.getIdPauta() );

        return pauta.build();
    }

    protected VotoId votoDTOToVotoId(VotoDTO votoDTO) {
        if ( votoDTO == null ) {
            return null;
        }

        VotoId.VotoIdBuilder votoId = VotoId.builder();

        votoId.associado( votoDTOToAssociado( votoDTO ) );
        votoId.pauta( votoDTOToPauta( votoDTO ) );

        return votoId.build();
    }

    private String votoIdAssociadoNome(VotoId votoId) {
        if ( votoId == null ) {
            return null;
        }
        Associado associado = votoId.getAssociado();
        if ( associado == null ) {
            return null;
        }
        String nome = associado.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private String votoIdAssociadoCpf(VotoId votoId) {
        if ( votoId == null ) {
            return null;
        }
        Associado associado = votoId.getAssociado();
        if ( associado == null ) {
            return null;
        }
        String cpf = associado.getCpf();
        if ( cpf == null ) {
            return null;
        }
        return cpf;
    }

    protected AssociadoBasicoDTO votoIdToAssociadoBasicoDTO(VotoId votoId) {
        if ( votoId == null ) {
            return null;
        }

        AssociadoBasicoDTO.AssociadoBasicoDTOBuilder<?, ?> associadoBasicoDTO = AssociadoBasicoDTO.builder();

        associadoBasicoDTO.nome( votoIdAssociadoNome( votoId ) );
        associadoBasicoDTO.cpf( votoIdAssociadoCpf( votoId ) );

        return associadoBasicoDTO.build();
    }

    private String votoIdPautaDescricao(VotoId votoId) {
        if ( votoId == null ) {
            return null;
        }
        Pauta pauta = votoId.getPauta();
        if ( pauta == null ) {
            return null;
        }
        String descricao = pauta.getDescricao();
        if ( descricao == null ) {
            return null;
        }
        return descricao;
    }

    private LocalDateTime votoIdPautaDtInicio(VotoId votoId) {
        if ( votoId == null ) {
            return null;
        }
        Pauta pauta = votoId.getPauta();
        if ( pauta == null ) {
            return null;
        }
        LocalDateTime dtInicio = pauta.getDataInicio();
        if ( dtInicio == null ) {
            return null;
        }
        return dtInicio;
    }

    private LocalDateTime votoIdPautaDtFim(VotoId votoId) {
        if ( votoId == null ) {
            return null;
        }
        Pauta pauta = votoId.getPauta();
        if ( pauta == null ) {
            return null;
        }
        LocalDateTime dtFim = pauta.getDataFim();
        if ( dtFim == null ) {
            return null;
        }
        return dtFim;
    }

    protected PautaBasicoDTO votoIdToPautaBasicoDTO(VotoId votoId) {
        if ( votoId == null ) {
            return null;
        }

        PautaBasicoDTO.PautaBasicoDTOBuilder<?, ?> pautaBasicoDTO = PautaBasicoDTO.builder();

        pautaBasicoDTO.descricao( votoIdPautaDescricao( votoId ) );
        pautaBasicoDTO.dataInicio( votoIdPautaDtInicio( votoId ) );
        pautaBasicoDTO.dataFim( votoIdPautaDtFim( votoId ) );

        return pautaBasicoDTO.build();
    }

    protected VotoEnumDTO votoEnumToVotoEnumDTO(VotoEnum votoEnum) {
        if ( votoEnum == null ) {
            return null;
        }

        VotoEnumDTO votoEnumDTO;

        switch ( votoEnum ) {
            case SIM: votoEnumDTO = VotoEnumDTO.SIM;
            break;
            case NAO: votoEnumDTO = VotoEnumDTO.NAO;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + votoEnum );
        }

        return votoEnumDTO;
    }
}
