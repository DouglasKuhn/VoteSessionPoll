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
@SequenceGenerator(name = "PAUTA_SEQ", sequenceName = "PAUTA_SEQ", allocationSize = 1)
public class Pauta {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAUTA_SEQ")
    private Long id;

    @NotNull(message = "Campo Obrigatório.")
    @NotBlank(message = "Campo Obrigatório.")
    @Column(name = "PAUTA", nullable = false)
    private String descricao;

    @NotNull(message = "Campo Obrigatório.")
    @Column(name = "INICIO", nullable = false)
    private LocalDateTime dtInicio;

    @NotNull(message = "Campo Obrigatório.")
    @Column(name = "FIM", nullable = false)
    private LocalDateTime dtFim;

    @NotNull(message = "Campo Obrigatório.")
    @Column(name = "EXCLUIDA", nullable = false)
    private boolean Excluida;

    @NotNull(message = "Campo Obrigatório.")
    @Column(name = "FINALIZADA", nullable = false)
    private boolean Finalizada;
}
