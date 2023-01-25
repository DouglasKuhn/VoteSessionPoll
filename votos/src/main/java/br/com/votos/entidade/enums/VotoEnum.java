package br.com.votos.entidade.enums;

import lombok.Getter;

@Getter
public enum VotoEnum {

    SIM("Sim"),
    NAO("Não");

    private String valor;

    VotoEnum(String valor) {
        this.valor = valor;
    }
}
