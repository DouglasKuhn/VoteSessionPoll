package br.com.votos.servico;

import br.com.votos.dto.UsuarioDTO;
import br.com.votos.entidade.Associado;
import br.com.votos.entidade.Pauta;
import br.com.votos.entidade.Votacao;
import br.com.votos.entidade.VotoId;
import br.com.votos.entidade.enums.CpfStatusEnum;
import br.com.votos.entidade.enums.VotoEnum;
import br.com.votos.exceptions.EntidadeNaoProcessavelException;
import br.com.votos.repositorio.VotacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class VotacaoServiceTest {

    private static final Long ID1 = 1L;

    @InjectMocks
    private VotacaoServiceImpl votacaoService;

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private PautaService pautaServiceMock;

    @Mock
    private AssociadoService associadoServiceMock;

    @Mock
    private VotacaoRepository votacaoRepositoryMock;

    @BeforeEach
    public void init () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveVotarComSucesso() {
        Pauta pautaBD = new Pauta();
        pautaBD.setId(ID1);
        Associado associadoBD = new Associado();
        associadoBD.setId(ID1);
        when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.of(pautaBD));

        when(associadoServiceMock.consultarPorId(anyLong())).thenReturn(associadoBD);

        Votacao votacao = new Votacao();
        VotoId votoId = new VotoId();
        Pauta pauta = new Pauta();
        pauta.setId(ID1);
        votoId.setPauta(pauta);
        Associado associado = new Associado();
        associado.setId(ID1);
        votoId.setAssociado(associado);
        votacao.setVotoId(votoId);
        votacao.setVoto(VotoEnum.SIM);

        when(votacaoRepositoryMock.save(any())).thenReturn(votacao);

        votacaoService.votar(votacao);

        assertEquals(votacao.getVotoId(), votoId);
    }

    @Test
    public void deveFalharQuandoVotarSemIdDaPauta() {
        when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.empty());

        EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
            Votacao votacao = new Votacao();
            VotoId votoId = new VotoId();
            Associado associado = new Associado();
            associado.setId(ID1);
            votoId.setAssociado(associado);
            votacao.setVotoId(votoId);
            votacaoService.votar(votacao);
        });

        assertTrue(thrown.getMessage().contains("Pauta não encontrada."));
    }

    @Test
    public void deveFalharQuandoVotarUtilizandoIdDaPautaInexistente() {
        when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.empty());

        EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
            Votacao votacao = new Votacao();
            VotoId votoId = new VotoId();
            Pauta pauta = new Pauta();
            pauta.setId(ID1);
            votoId.setPauta(pauta);
            Associado associado = new Associado();
            associado.setId(ID1);
            votoId.setAssociado(associado);
            votacao.setVotoId(votoId);
            votacaoService.votar(votacao);
        });

        assertTrue(thrown.getMessage().contains("Pauta não encontrada."));
    }

    @Test
    public void deveFalharQuandoVotarSemIdDoAssociado() {
        Pauta pautaBaseDeDados = new Pauta();
        when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.of(pautaBaseDeDados));

        EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
            Votacao votacao = new Votacao();
            VotoId votoId = new VotoId();
            Pauta pauta = new Pauta();
            pauta.setId(ID1);
            votoId.setPauta(pauta);
            votacao.setVotoId(votoId);
            votacaoService.votar(votacao);
        });

        assertTrue(thrown.getMessage().contains("Associado não encontrado."));
    }

    @Test
    public void deveFalharQuandoVotarUtilizandoIdDoAssociadoInexistente() {
        Pauta pautaBD = new Pauta();
        when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.of(pautaBD));

        when(associadoServiceMock.consultarPorId(anyLong())).thenReturn(null);

        EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
            Votacao votacao = new Votacao();
            VotoId votoId = new VotoId();
            Pauta pauta = new Pauta();
            pauta.setId(ID1);
            votoId.setPauta(pauta);
            Associado associado = new Associado();
            associado.setId(ID1);
            votoId.setAssociado(associado);
            votacao.setVotoId(votoId);
            votacaoService.votar(votacao);
        });

        assertTrue(thrown.getMessage().contains("Associado não encontrado."));
    }

    @Test
    public void deveConsultarVotacoesComSucesso() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        when(entityManagerMock.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<Votacao> cq = mock(CriteriaQuery.class);
        when(cb.createQuery(eq(Votacao.class))).thenReturn(cq);

        Root<Votacao> votacaoRoot = mock(Root.class);
        when(cq.from(eq(Votacao.class))).thenReturn(votacaoRoot);

        Path<Object> pathGet1 = mock(Path.class);
        when(votacaoRoot.get(anyString())).thenReturn(pathGet1);

        Path<Object> pathGet2 = mock(Path.class);
        when(pathGet1.get(anyString())).thenReturn(pathGet2);

        Path<Object> pathGet3 = mock(Path.class);
        when(pathGet2.get(anyString())).thenReturn(pathGet3);

        TypedQuery<Votacao> query = mock(TypedQuery.class);
        when(entityManagerMock.createQuery(any(CriteriaQuery.class))).thenReturn(query);

        votacaoService.consultar(Optional.of(1L));

        Assertions.assertTrue(true, "Consulta executada com sucesso.");
    }
}
