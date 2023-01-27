package br.com.votos.repositorio;

import br.com.votos.entidade.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

        Optional<Votacao> findByVotoIdPautaId(Long idPauta);

        Optional<Votacao> findByVotoIdPautaIdAndVotoIdAssociadoId(Long idPauta, Long idAssociado);
}
