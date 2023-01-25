package br.com.votos.entidade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ASSOCIADO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "ASSOCIADO_SEQ", sequenceName = "ASSOCIADO_SEQ", allocationSize = 1)
public class Associado {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ASSOCIADO_SEQ")
    private Long id;

    @NotNull(message = "Campo Obrigatório.")
    @NotBlank(message = "Campo Obrigatório.")
    @Column(name = "NOME", nullable = false)
    private String nome;

    @NotNull(message = "Campo Obrigatório.")
    @NotBlank(message = "Campo Obrigatório.")
    @Column(name = "CPF", nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "Campo Obrigatório.")
    @Column(name = "EXCLUIDO", nullable = false)
    private boolean Excluido;
}
