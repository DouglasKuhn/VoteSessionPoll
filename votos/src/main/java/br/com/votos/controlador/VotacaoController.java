package br.com.votos.controlador;

import br.com.votos.dto.VotacaoDTO;
import br.com.votos.dto.VotoDTO;
import br.com.votos.entidade.Votacao;
import br.com.votos.entidade.enums.VotoEnum;
import br.com.votos.mapper.VotacaoMapper;
import br.com.votos.servico.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/votacao", produces = MediaType.APPLICATION_JSON_VALUE)
public class VotacaoController {

    @Autowired
    private VotacaoService votacaoService;

    @Autowired
    private VotacaoMapper votacaoMapper;

    @PostMapping
    public ResponseEntity<Void> votar (@RequestParam(value = "ID da Pauta")Long idPauta,
                                      @RequestParam(value = "ID do Associado")Long idAssociado,
                                      @RequestParam(value = "Seu Voto")VotoEnum voto) {
        VotoDTO votoDTO = new VotoDTO(idAssociado, idPauta, voto);
        Votacao votacao = this.votacaoService.votar(this.votacaoMapper.toVotacao(votoDTO));
        if (votacao != null) {
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().queryParam("idPauta", votoDTO.getIdPauta()).queryParam("idAssociado", votoDTO.getIdAssociado()).queryParam("voto", votoDTO.getVoto()).build().toUri()).build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<VotacaoDTO>> consultar (@RequestParam(value = "ID da Pauta")Optional<Long> idPauta) {
        List<Votacao> votacoes = this.votacaoService.consultar(idPauta);
        if (!votacoes.isEmpty() && votacoes != null) {
            return ResponseEntity.ok(this.votacaoMapper.toVotacaoDtoList(votacoes));
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
