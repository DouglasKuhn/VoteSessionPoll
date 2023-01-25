package br.com.votos.entidade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class VotoId {

    @NotNull(message = "Campo obrigatório.")
    @JoinColumn(name = "FK_ASSOCIADO")
    @ManyToOne
    public Associado associado;

    @NotNull(message = "Campo obrigatório.")
    @JoinColumn(name = "FK_PAUTA")
    @ManyToOne
    public Pauta pauta;
}
