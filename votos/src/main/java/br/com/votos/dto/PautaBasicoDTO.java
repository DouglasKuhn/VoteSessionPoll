package br.com.votos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PautaBasicoDTO {

    @NotNull(message = "Campo obrigatório.")
    @NotBlank(message = "Campo obrigatório.")
    private String descricao;

    @FutureOrPresent(message = "Campo data inicío deve ser após a data atual.")
    @NotNull(message = "Campo obrigatório.")
    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;
}
