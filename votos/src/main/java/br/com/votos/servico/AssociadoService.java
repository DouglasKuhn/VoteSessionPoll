package br.com.votos.servico;

import br.com.votos.entidade.Associado;

import java.util.List;

public interface AssociadoService {
    Associado criar (Associado associado);

    Associado alterar (Long id, Associado associadoAlterado);

    List<Associado> consultar(Long id, String nome, Boolean excluido);

    Associado consultarPorId (Long id);

    void excluir (Long id);
}
