package br.com.votos.dto;

import br.com.votos.dto.enums.VotoEnumDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VotacaoDTO {

    @NotNull(message = "Campo obrigatório.")
    private AssociadoBasicoDTO associado;

    @NotNull(message = "Campo obrigatório.")
    private PautaBasicoDTO pauta;

    @NotNull(message = "Campo obrigatório.")
    private VotoEnumDTO voto;
}
