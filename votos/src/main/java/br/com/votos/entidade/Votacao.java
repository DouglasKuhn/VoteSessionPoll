package br.com.votos.entidade;

import br.com.votos.entidade.enums.VotoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "VOTACAO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Votacao {

    @EmbeddedId
    private VotoId votoId;

    @NotNull(message = "O campo voto é obrigatório.")
    @Column(name = "VOTO", nullable = false)
    @Enumerated(EnumType.STRING)
    private VotoEnum voto;
}
