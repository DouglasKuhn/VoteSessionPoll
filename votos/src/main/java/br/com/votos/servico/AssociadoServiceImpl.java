package br.com.votos.servico;

import br.com.votos.entidade.Associado;
import br.com.votos.exceptions.ObjetoNaoEncontradoException;
import br.com.votos.repositorio.AssociadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AssociadoServiceImpl implements AssociadoService{

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Associado criar (String nome,  String cpf) {
        Associado associado = new Associado();
        associado.setNome(nome);
        associado.setCpf(cpf);
        log.info("Associado criado: {}", associado);
        return this.associadoRepository.save(associado);
    }

    @Override
    public Associado alterar (Long id, Associado associadoAlterado) {
        log.debug("idAssociado: {}, associadoAlterado: {}", id, associadoAlterado);
        Optional<Associado> associadoDoBDOptional = this.associadoRepository.findById(id);
        if (associadoDoBDOptional.isPresent()) {
            Associado associado = associadoDoBDOptional.get();
            associadoAlterado.setId(id);
            log.info("Associado da base de dados: {}", associado);
            log.info("Associado alterado: {}", associadoAlterado);
            return this.associadoRepository.save(associadoAlterado);
        } else {
            log.warn("Objeto informado não foi encontrado. idAssociado: {}", id);
            throw new ObjetoNaoEncontradoException();
        }
    }

    @Override
    public List<Associado> consultar(Optional<String> id, Optional<String> nome) {
        log.debug("idAssociado: {}, nome: {}, excluido: {}", id, nome);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Associado> cq = cb.createQuery(Associado.class);
        Root<Associado> associadoRoot = cq.from(Associado.class);

        List<Predicate> predicateList = new ArrayList<>();
        if (id.isPresent()) {
            predicateList.add(cb.equal(associadoRoot.get("id"), id.get()));
        }

        if (nome.isPresent()) {
            predicateList.add(cb.like(associadoRoot.get("nome"), "%" + nome.get() + "%"));
        }

        cq.where(predicateList.stream().toArray(Predicate[]::new));

        return this.entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Optional<Associado> consultarPorId (Long id) {
        log.debug("idAssociado: {}", id);
        return this.associadoRepository.findById(id);
    }

    @Override
    public void excluir (Long id) {
        log.debug("idAssociado: {}", id);
        Optional<Associado> associadoOptional = this.associadoRepository.findById(id);
        if (associadoOptional.isPresent()) {
            Associado associado = associadoOptional.get();
            log.info("Associado excluído: {}", associado);
            this.associadoRepository.delete(associado);
        } else {
            log.warn("Objeto informado não foi encontrado. idAssociado: {}", id);
            throw new ObjetoNaoEncontradoException();
        }
    }
}
