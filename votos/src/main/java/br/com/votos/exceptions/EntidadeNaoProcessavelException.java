package br.com.votos.exceptions;

public class EntidadeNaoProcessavelException extends RuntimeException {

    public EntidadeNaoProcessavelException(String message) {
        super(message);
    }
}
