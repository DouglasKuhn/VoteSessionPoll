package br.com.votos.servico;

import br.com.votos.entidade.Associado;

import java.util.List;
import java.util.Optional;

public interface AssociadoService {
    Associado criar (String nome, String cpf);

    Associado alterar (Long id, Associado associadoAlterado);

    List<Associado> consultar(Optional<String> id, Optional<String> nome);

    Optional<Associado> consultarPorId (Long id);

    void excluir (Long id);
}
