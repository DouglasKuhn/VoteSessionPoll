package br.com.votos.repositorio;

import br.com.votos.entidade.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    @Query("SELECT a FROM Associado a WHERE a.cpf = :cpf")
    Associado findByCpf (String cpf);
}
