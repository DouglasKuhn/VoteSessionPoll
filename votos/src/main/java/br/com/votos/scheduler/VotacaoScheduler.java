package br.com.votos.scheduler;

import br.com.votos.dto.VotacaoResultadoDTO;
import br.com.votos.dto.enums.VotoEnumDTO;
import br.com.votos.entidade.Pauta;
import br.com.votos.entidade.Votacao;
import br.com.votos.entidade.enums.VotoEnum;
import br.com.votos.mapper.AssociadoMapper;
import br.com.votos.mapper.PautaMapper;
import br.com.votos.servico.PautaService;
import br.com.votos.servico.VotacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@EnableScheduling
@Slf4j
public class VotacaoScheduler {

    private static final String MINUTO = "60000";

    @Autowired
    private VotacaoService votacaoService;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private PautaMapper pautaMapper;

    @Autowired
    private AssociadoMapper associadoMapper;

    @Scheduled(fixedDelayString = MINUTO)
    public void finalizarPautas() {
        log.info("Processamento iniciado.");
        List<Pauta> pautas = pautaService.consultarPautasNaoFinalizadas();
        for (Pauta pauta : pautas) {
            if (LocalDateTime.now().isAfter(pauta.getDataFim())) {
                pauta.setFinalizada(true);
                this.pautaService.alterar(pauta.getId(), pauta);
                log.info("Pauta finalizada: {}", pauta);
            }
        }
    }

    private List<VotacaoResultadoDTO> votacaoResultadoDTOList(Pauta pauta) {
        List<VotacaoResultadoDTO> votacaoResultadoDTOList = new ArrayList<>();
        Optional<VotacaoResultadoDTO> votacaoResultadoDtoSimOptional = votacaoResultado(pauta, VotoEnum.SIM);
        if (votacaoResultadoDtoSimOptional.isPresent()) {
            votacaoResultadoDTOList.add(votacaoResultadoDtoSimOptional.get());
        }
        Optional<VotacaoResultadoDTO> votacaoResultadoDtoNaoOptional = votacaoResultado(pauta, VotoEnum.NAO);
        if (votacaoResultadoDtoNaoOptional.isPresent()) {
            votacaoResultadoDTOList.add(votacaoResultadoDtoNaoOptional.get());
        }
        log.info("Message {} ", votacaoResultadoDTOList);
        return votacaoResultadoDTOList;
    }

    private Optional<VotacaoResultadoDTO> votacaoResultado(Pauta pauta, VotoEnum voto) {
        List<Votacao> votacaoSimListOptional = votacaoService.consultar(Optional.of(pauta.getId()));
        if (!votacaoSimListOptional.isEmpty() && votacaoSimListOptional != null) {
            List<Votacao> votacaoSimList = votacaoSimListOptional;
            VotacaoResultadoDTO votacaoResultadoSimDTO = new VotacaoResultadoDTO();
            votacaoResultadoSimDTO.setVoto(VotoEnumDTO.valueOf(voto.name()));
            votacaoResultadoSimDTO.setPauta(this.pautaMapper.toPautaCompletoDto(pauta));
            votacaoResultadoSimDTO.setAssociados(new ArrayList<>());
            for (Votacao votacao : votacaoSimList) {
                votacaoResultadoSimDTO.getAssociados().add(this.associadoMapper.toAssociadoCompletoDto(votacao.getVotoId().getAssociado()));
            }
            votacaoResultadoSimDTO.setQtdVotos(votacaoResultadoSimDTO.getAssociados().size());
            return Optional.of(votacaoResultadoSimDTO);
        }
        return Optional.empty();
    }
}
