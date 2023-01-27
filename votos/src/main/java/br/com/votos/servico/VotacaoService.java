package br.com.votos.servico;

import br.com.votos.entidade.Votacao;
import br.com.votos.entidade.enums.VotoEnum;

import java.util.List;
import java.util.Optional;

public interface VotacaoService {

    Votacao votar(Votacao votacao);

    List<Votacao> consultar(Optional<Long> idPauta);
}
