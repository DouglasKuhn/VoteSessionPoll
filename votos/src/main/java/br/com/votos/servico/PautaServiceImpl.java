package br.com.votos.servico;

import br.com.votos.entidade.Pauta;
import br.com.votos.exceptions.EntidadeNaoProcessavelException;
import br.com.votos.exceptions.ObjetoNaoEncontradoException;
import br.com.votos.repositorio.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PautaServiceImpl implements PautaService {

    @Autowired
    private final EntityManager entityManager;

    @Autowired
    private final PautaRepository pautaRepository;

    @Override
    public Pauta criar(String descricao, Long time) {
        Pauta pauta = new Pauta(null, descricao, LocalDateTime.now(), null, false);
        if (time != null && time > 0) {
            pauta.setDataFim(pauta.getDataInicio().plusMinutes(time));
        } else {
            pauta.setDataFim(pauta.getDataInicio().plusMinutes(1));
        }
        log.info("Pauta criada: {}", pauta);
        return this.pautaRepository.save(pauta);
    }

    @Override
    public Pauta alterar(Long id, Pauta pautaAlterada) {
        log.debug("idPauta: {}, pautaAlterada: {}", id, pautaAlterada);
        Optional<Pauta> pautaDoBDOptional = this.pautaRepository.findById(id);
        if (pautaDoBDOptional.isPresent()) {
            Pauta pauta = pautaDoBDOptional.get();
            validarSePautaFoiFinalizada(pauta);
            pautaAlterada.setId(id);
            log.info("Pauta da base de dados: {}", pauta);
            log.info("Pauta alterada: {}", pautaAlterada);
            return this.pautaRepository.save(pautaAlterada);
        } else {
            log.warn("Objeto informado não foi encontrado. idPauta: {}", id);
            throw new ObjetoNaoEncontradoException();
        }
    }

    @Override
    public List<Pauta> consultar(Optional<String> id, Optional<String> descricao) {
        log.debug("idPauta: {}, descricao: {}", id, descricao);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Pauta> cq = cb.createQuery(Pauta.class);
        Root<Pauta> pautaRoot = cq.from(Pauta.class);

        List<Predicate> predicateList = new ArrayList<>();
        if (id.isPresent()) {
            predicateList.add(cb.equal(pautaRoot.get("id"), id.get()));
        }

        if (descricao.isPresent()) {
            predicateList.add(cb.like(pautaRoot.get("descricao"), "%" + descricao.get() + "%"));
        }

        cq.where(predicateList.stream().toArray(Predicate[]::new));

        return this.entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Optional<Pauta> consultarPorId(Long id) {
        log.debug("idPauta: {}", id);
        return pautaRepository.findById(id);
    }

    @Override
    public List<Pauta> consultarAsNaoFinalizadas() {
        return pautaRepository.findByFinalizadaFalse();
    }

    @Override
    public void excluir(Long id) {
        log.debug("idPauta: {}", id);
        Optional<Pauta> pautaOptional = this.pautaRepository.findById(id);
        if (pautaOptional.isPresent()) {
            Pauta pauta = pautaOptional.get();
            log.info("Pauta excluída: {}", pauta);
            this.pautaRepository.delete(pauta);
        } else {
            log.warn("Objeto informado não foi encontrado. idPauta: {}", id);
            throw new ObjetoNaoEncontradoException();
        }
    }

    private void validarSePautaFoiFinalizada(Pauta pauta) {
        if (pauta.isFinalizada()) {
            log.warn("A pauta já foi finalizada. Pauta: {}", pauta);
            throw new EntidadeNaoProcessavelException("A pauta já foi finalizada.");
        }
    }
}
