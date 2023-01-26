package br.com.votos.servico;


import br.com.votos.entidade.Pauta;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PautaService {

    Pauta criar(String descricao, Long time);

    Pauta alterar(Long id, Pauta pauta);

    List<Pauta> consultar(Optional<String> id, Optional<String> descricao);

    Optional<Pauta> consultarPorId(Long id);

    List<Pauta> consultarAsNaoFinalizadas();

    void excluir(Long id);
}
