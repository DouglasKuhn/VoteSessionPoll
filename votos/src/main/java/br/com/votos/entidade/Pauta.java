package br.com.votos.entidade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAUTA")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pauta {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Campo Obrigatório.")
    @NotBlank(message = "Campo Obrigatório.")
    @Column(name = "PAUTA", nullable = false)
    private String descricao;

    @NotNull(message = "Campo Obrigatório.")
    @Column(name = "INICIO", nullable = false)
    private LocalDateTime dataInicio;

    @NotNull(message = "Campo Obrigatório.")
    @Column(name = "FIM", nullable = false)
    private LocalDateTime dataFim;

    @NotNull(message = "Campo Obrigatório.")
    @Column(name = "FINALIZADA", nullable = false)
    private boolean finalizada;
}
