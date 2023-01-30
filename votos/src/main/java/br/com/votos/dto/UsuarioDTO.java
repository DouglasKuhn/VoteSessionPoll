package br.com.votos.dto;

import br.com.votos.entidade.enums.CpfStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private CpfStatusEnum status;
}
