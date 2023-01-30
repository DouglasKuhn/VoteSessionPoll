package br.com.votos.controlador;

import br.com.votos.dto.AssociadoBasicoDTO;
import br.com.votos.dto.AssociadoCompletoDTO;
import br.com.votos.entidade.Associado;
import br.com.votos.mapper.AssociadoMapper;
import br.com.votos.servico.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/associado", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociadoController {

    @Autowired
    private AssociadoService associadoService;

    @Autowired
    private AssociadoMapper associadoMapper;

    @PostMapping
    public ResponseEntity<Void> criar (@RequestParam(value = "Nome do Associado") String nome,
                                       @RequestParam(value = "CPF do Associado") String cpf) {
        Associado associado = this.associadoService.criar(nome, cpf);
        if (associado != null) {
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().queryParam("id", associado.getId().toString()).build().toUri()).build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> alterar (@PathVariable(value = "id") Long id,
                                         @Valid @RequestBody AssociadoBasicoDTO associadoBasicoDTO) {
        Associado associado = this.associadoService.alterar(id, this.associadoMapper.toAssociado(associadoBasicoDTO));
        if (associado != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AssociadoCompletoDTO>> consultar (@RequestParam(value = "ID do Associado") Optional<String> id,
                                                                 @RequestParam(value = "Nome do Associado") Optional<String> nome) {
        List<Associado> associados = this.associadoService.consultar(id, nome);
        if (!associados.isEmpty() && associados != null) {
            return ResponseEntity.ok(this.associadoMapper.toAssociadoCompletoDtoList(associados));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> excluir (@RequestParam(value = "ID") Long id) {
        associadoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
