package br.com.votos.servico;


import br.com.votos.entidade.Pauta;

import java.util.List;
import java.util.Optional;

public interface PautaService {

    Pauta criar(String descricao, Long time);

    Pauta alterar(Long id, Pauta pauta);

    List<Pauta> consultar(Optional<String> id, Optional<String> descricao);

    Optional<Pauta> consultarPorId(Long id);

    List<Pauta> consultarPautasNaoFinalizadas();

    void excluir(Long id);
}
