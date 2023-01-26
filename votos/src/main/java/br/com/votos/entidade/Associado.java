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
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "Campo Obrigatório.")
    @NotBlank(message = "Campo Obrigatório.")
    @Column(name = "NOME", nullable = false)
    private String nome;

    @NotNull(message = "Campo Obrigatório.")
    @NotBlank(message = "Campo Obrigatório.")
    @Column(name = "CPF", nullable = false, unique = true)
    private String cpf;
}
