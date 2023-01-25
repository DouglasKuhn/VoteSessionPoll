package br.com.votos.controlador;

import br.com.votos.dto.AssociadoBasicoDTO;
import br.com.votos.dto.AssociadoCompletoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/associado", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AssociadoControllerImpl implements AssociadoController {

    @PostMapping
    public ResponseEntity<Void> criar (AssociadoBasicoDTO associadoBasicoDTO) {
        return null;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> alterar (Long id, AssociadoBasicoDTO associadoBasicoDTO) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<AssociadoCompletoDTO>> consultar (Long id, String nome, Boolean excluido) {
        return null;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> excluir(Long id) {
        return null;
    }

}
