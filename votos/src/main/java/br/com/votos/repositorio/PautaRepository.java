package br.com.votos.repositorio;

import br.com.votos.entidade.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    List<Pauta> findByFinalizadaFalse();
}
