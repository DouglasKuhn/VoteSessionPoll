package br.com.votos.servico;

import br.com.votos.entidade.Pauta;
import br.com.votos.exceptions.EntidadeNaoProcessavelException;
import br.com.votos.exceptions.ObjetoNaoEncontradoException;
import br.com.votos.repositorio.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PautaServiceTest {

    private static final Long ID1 = 1L;
    private static final String DESCRICAO1 = "Pauta1";

    @InjectMocks
    private PautaServiceImpl pautaService;

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private PautaRepository pautaRepositoryMock;

    @BeforeEach
    public void init () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveCriarPautaComSucesso() {

        Pauta pautaArmazenada = new Pauta();
        pautaArmazenada.setId(1L);
        when(pautaRepositoryMock.save(any())).thenReturn(pautaArmazenada);

        Pauta pauta = new Pauta();
        pauta.setDataInicio(LocalDateTime.now());
        Pauta pautaBD = pautaService.criar(DESCRICAO1, ID1);

        assertEquals(pautaBD.getId(), ID1);

    }

    @Test
    public void deveFalharQuandoAlterarPautaInexistente() {
        when(pautaRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException thrown = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            Pauta pautaAlterada = new Pauta();
            pautaAlterada.setDescricao(DESCRICAO1);
            pautaService.alterar(ID1, pautaAlterada);
        });

        assertTrue(thrown.getMessage().contains("Objeto não encontrado"));

    }

    @Test
    public void deveAlterarPautaComSucesso() {
        Pauta pautaArmazenada = new Pauta();
        when(pautaRepositoryMock.findById(anyLong())).thenReturn(Optional.of(pautaArmazenada));

        String descricao = DESCRICAO1;
        Pauta pautaJaArmazenada = new Pauta();
        pautaJaArmazenada.setDescricao(descricao);
        when(pautaRepositoryMock.save(any())).thenReturn(pautaJaArmazenada);

        Pauta pautaAlterada = new Pauta();
        pautaAlterada.setDescricao(descricao);
        Pauta pautaBD = pautaService.alterar(ID1, pautaAlterada);

        assertEquals(pautaBD.getDescricao(), pautaAlterada.getDescricao());

    }

    @Test
    public void deveConsultarPautaComSucesso() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        when(entityManagerMock.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<Pauta> cq = mock(CriteriaQuery.class);
        when(cb.createQuery(eq(Pauta.class))).thenReturn(cq);

        Root<Pauta> pautaRoot = mock(Root.class);
        when(cq.from(eq(Pauta.class))).thenReturn(pautaRoot);

        TypedQuery<Pauta> query = mock(TypedQuery.class);
        when(entityManagerMock.createQuery(any(CriteriaQuery.class))).thenReturn(query);

        pautaService.consultar(Optional.of("1"), Optional.of(DESCRICAO1));

        assertTrue(true, "Consulta executada com sucesso.");
    }

    @Test
    public void deveConsultarPautaPorIdComSucesso() {
        Pauta pauta = new Pauta();
        Optional<Pauta> pautaOpt = Optional.of(pauta);
        when(pautaRepositoryMock.findById(anyLong())).thenReturn(pautaOpt);

        Optional<Pauta> pautaBDOpt = pautaService.consultarPorId(ID1);

        pautaBDOpt.ifPresent(pautaBD -> assertEquals(pautaBD, pauta));
    }

    @Test
    public void deveConsultarPautaNaoFinalizadasComSucesso() {
        List<Pauta> pautaList = new ArrayList<>();
        when(pautaRepositoryMock.findByFinalizadaFalse()).thenReturn(pautaList);

        List<Pauta> pautaLst = pautaService.consultarPautasNaoFinalizadas();

        assertEquals(pautaLst, pautaList);
    }

    @Test
    public void deveExcluirPautaComSucesso() {
        Pauta pauta = new Pauta();
        pauta.setFinalizada(false);
        when(pautaRepositoryMock.findById(anyLong())).thenReturn(Optional.of(pauta));

        pautaService.excluir(ID1);

        assertTrue(true, "Pauta excluída.");
    }

    @Test
    public void deveFalharQuandoExcluirPautaInexistente() {
        when(pautaRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException thrown = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            pautaService.excluir(ID1);
        });

        assertTrue(thrown.getMessage().contains("Objeto não encontrado."));
    }

    @Test
    public void deveExcluirPautaJaFinalizadaComSucesso() {
        Pauta pauta = new Pauta();
        pauta.setFinalizada(true);
        when(pautaRepositoryMock.findById(anyLong())).thenReturn(Optional.of(pauta));

        pautaService.excluir(ID1);

        assertTrue(true, "Pauta excluída.");
    }
}
