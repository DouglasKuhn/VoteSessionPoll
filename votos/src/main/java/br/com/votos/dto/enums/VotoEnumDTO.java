package br.com.votos.dto.enums;

import lombok.Getter;

@Getter
public enum VotoEnumDTO {

    SIM("Sim"),
    NAO("Não");

    private String valor;

    VotoEnumDTO(String valor) {
        this.valor = valor;
    }
}
