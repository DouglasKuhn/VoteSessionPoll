package br.com.votos.dto;

import br.com.votos.dto.enums.VotoEnumDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VotacaoResultadoDTO {

    private PautaCompletoDTO pauta;

    private VotoEnumDTO voto;

    private Integer qtdVotos;

    private List<AssociadoCompletoDTO> associados;
}
