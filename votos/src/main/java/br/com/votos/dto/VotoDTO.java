package br.com.votos.dto;

import br.com.votos.entidade.enums.VotoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {

    @NotNull(message = "O campo idAssociado é obrigatório.")
    private Long idAssociado;

    @NotNull(message = "O campo idPauta é obrigatório.")
    private Long idPauta;

    @NotNull(message = "O campo voto é obrigatório.")
    private VotoEnum voto;
}
