package br.com.votos.servico;

import br.com.votos.entidade.Associado;
import br.com.votos.entidade.Pauta;
import br.com.votos.entidade.Votacao;
import br.com.votos.entidade.VotoId;
import br.com.votos.entidade.enums.VotoEnum;
import br.com.votos.exceptions.EntidadeNaoProcessavelException;
import br.com.votos.repositorio.VotacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
public class VotacaoServiceImpl implements VotacaoService{

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoService associadoService;

    @Override
    public Votacao votar(Votacao votacao) {
        log.debug("Votacao: {}", votacao);
        VotoId votoId = new VotoId();
        votoId.setPauta(buscarPauta(votacao));
        votoId.setAssociado(buscarAssociado(votacao));
        votacao.setVotoId(votoId);
        validarVotoJaRealizado(votoId.getPauta().getId(), votoId.getAssociado().getId());
        log.info("Voto registrado: {}", votacao);
        return this.votacaoRepository.save(votacao);
    }

    @Override
    public List<Votacao> consultar(Optional<Long> idPauta) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Votacao> cq = cb.createQuery(Votacao.class);
        Root<Votacao> votacaoRoot = cq.from(Votacao.class);

        List<Predicate> predicateList = new ArrayList<>();

        if (idPauta.isPresent()) {
            predicateList.add(cb.equal(votacaoRoot.get("votoId").get("pauta").get("id"), idPauta.get()));
        }

        cq.where(predicateList.stream().toArray(Predicate[]::new));

        return this.entityManager.createQuery(cq).getResultList();
    }

    private void validarVotoJaRealizado(Long idPauta, Long idAssociado) {
        Optional<Votacao> votacaoBaseDeDadosOptional = votacaoRepository.findByVotoIdPautaIdAndVotoIdAssociadoId(idPauta, idAssociado);
        if (votacaoBaseDeDadosOptional.isPresent()) {
            log.warn("O associado já realizou seu voto. idPauta: {}, idAssociado: {}", idPauta, idAssociado);
            throw new EntidadeNaoProcessavelException("O associado já realizou seu voto.");
        }
    }

    private Associado buscarAssociado(Votacao votacao) {
        if (!ObjectUtils.isEmpty(votacao) && !ObjectUtils.isEmpty(votacao.getVotoId())
                && !ObjectUtils.isEmpty(votacao.getVotoId().getAssociado())) {
            Optional<Associado> associadoOptional = associadoService
                    .consultarPorId(votacao.getVotoId().getAssociado().getId());
            if (associadoOptional.isPresent()) {
                Associado associado = associadoOptional.get();
                return associado;
            } else {
                log.warn("O associado não foi encontrado ou processado. idAssociado: {}", votacao.getVotoId().getAssociado().getId());
                throw new EntidadeNaoProcessavelException("O associado não foi encontrado ou processado.");
            }
        } else {
            log.warn("O associado não foi encontrado ou processado. votacao: {}", votacao);
            throw new EntidadeNaoProcessavelException("O associado não foi encontrado ou processado.");
        }
    }

    private Pauta buscarPauta(Votacao votacao) {
        if (!ObjectUtils.isEmpty(votacao) && !ObjectUtils.isEmpty(votacao.getVotoId())
                && !ObjectUtils.isEmpty(votacao.getVotoId().getPauta())) {
            Optional<Pauta> pautaOptional = pautaService.consultarPorId(votacao.getVotoId().getPauta().getId());
            if (pautaOptional.isPresent()) {
                return pautaOptional.get();
            } else {
                log.warn("A pauta não foi encontrada ou processada. idPauta: {}", votacao.getVotoId().getPauta().getId());
                throw new EntidadeNaoProcessavelException("A pauta não foi encontrada ou processada.");
            }
        } else {
            log.warn("A pauta não foi encontrada ou processada. votacao: {}", votacao);
            throw new EntidadeNaoProcessavelException("A pauta não foi encontrada ou processada.");
        }
    }

}
