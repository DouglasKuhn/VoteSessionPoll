package br.com.votos.controlador;

import br.com.votos.dto.AssociadoBasicoDTO;
import br.com.votos.dto.AssociadoCompletoDTO;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

public interface AssociadoController {

    ResponseEntity<Void> criar (AssociadoBasicoDTO associadoBasicoDTO);

    ResponseEntity<Void> alterar (Long id, AssociadoBasicoDTO associadoBasicoDTO);

    ResponseEntity<List<AssociadoCompletoDTO>> consultar (Long id, String nome, Boolean excluido);

    public ResponseEntity<Void> excluir(Long id);
}
