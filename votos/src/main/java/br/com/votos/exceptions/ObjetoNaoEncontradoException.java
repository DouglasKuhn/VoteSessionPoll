package br.com.votos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjetoNaoEncontradoException extends RuntimeException {

    public ObjetoNaoEncontradoException() {
        super("Objeto não encontrado.");
    }
}
