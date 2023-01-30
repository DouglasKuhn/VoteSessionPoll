package br.com.votos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoBasicoDTO {

    @NotNull(message = "Campo obrigatório.")
    @NotBlank(message = "Campo obrigatório.")
    private String nome;

    @NotNull(message = "Campo obrigatório.")
    @NotBlank(message = "Campo obrigatório.")
    private String cpf;
}
