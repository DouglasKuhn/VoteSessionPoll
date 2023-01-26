package br.com.votos.controlador;

import br.com.votos.dto.PautaBasicoDTO;
import br.com.votos.dto.PautaCompletoDTO;
import br.com.votos.entidade.Pauta;
import br.com.votos.mapper.PautaMapper;
import br.com.votos.servico.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/pauta", produces = MediaType.APPLICATION_JSON_VALUE)
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @Autowired
    private PautaMapper pautaMapper;

    @PostMapping
    public ResponseEntity<Void> criar (@RequestParam(value = "Descrição da Pauta") String descricao,
                                      @RequestParam(value = "Tempo de duração em Minutos") Long time) {
        Pauta pauta = this.pautaService.criar(descricao, time);
        if (pauta != null) {
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().queryParam("id", pauta.getId().toString()).build().toUri()).build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> alterar (@PathVariable(value = "ID") Long id,
                                        @Valid @RequestBody PautaBasicoDTO pautaDto) {
        Pauta pauta = this.pautaService.alterar(id, this.pautaMapper.toPauta(pautaDto));
        if (pauta != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PautaCompletoDTO>> consultar (@RequestParam(value = "ID da Pauta") Optional<String> id,
                                                             @RequestParam(value = "Descrição da Pauta") Optional<String> descricao) {
        List<Pauta> pautas = this.pautaService.consultar(id, descricao);
        if (!pautas.isEmpty() && pautas != null) {
            return ResponseEntity.ok(this.pautaMapper.toPautaCompletoDtoList(pautas));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> excluir (@PathVariable(value = "ID") Long id) {
        pautaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
