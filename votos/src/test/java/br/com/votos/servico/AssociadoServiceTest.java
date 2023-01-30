package br.com.votos.servico;

import br.com.votos.entidade.Associado;
import br.com.votos.exceptions.EntidadeNaoProcessavelException;
import br.com.votos.exceptions.ObjetoNaoEncontradoException;
import br.com.votos.repositorio.AssociadoRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AssociadoServiceTest {

    private static final Long ID1 = 1L;
    private static final Long ID2 = 2L;
    private static final String NOME1 = "Associado1";
    private static final String NOME2 = "Associado2";
    private static final String NOME3 = "Associado3";
    private static final String CPF1 = "70552168181";
    private static final String CPF2 = "88333512208";

    @InjectMocks
    private AssociadoServiceImpl associadoService;

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private AssociadoRepository associadoRepositoryMock;

    @BeforeEach
    public void init () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveFalharQuandoCriarAssociadoComUmCpfJaExistente() {
        when(associadoRepositoryMock.findByCpf(anyString()))
                .thenReturn(new Associado(ID1,NOME1, CPF1));
        try {
            associadoService.criar(NOME2, CPF1);
        } catch (Exception ex) {
            assertEquals(EntidadeNaoProcessavelException.class, ex.getClass());
        }
    }

    @Test
    public void deveCriarAssociadoComSucesso() {
        when(associadoRepositoryMock.findByCpf(anyString())).thenReturn(null);

        when(associadoRepositoryMock.save(any())).thenReturn(new Associado(ID1, NOME1, CPF1));

        Associado associadoDoBD = associadoService.criar(NOME1, CPF1);

        assertEquals(associadoDoBD.getId(), ID1);
    }

    @Test
    public void deveFalharQuandoAlterarAssociadoInexistente() {
        when(associadoRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException thrown = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            Associado associadoAlterado = new Associado();
            associadoAlterado.setNome(NOME1);
            associadoAlterado.setCpf(CPF1);
            associadoService.alterar(ID1, associadoAlterado);
        });

        assertTrue(thrown.getMessage().contains("Objeto não encontrado."));
    }

    @Test
    public void deveFalharQuandoAlterarAssociadoComCpfJaExistente() {
        when(associadoRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(new Associado(ID1, NOME1, CPF1)));

        when(associadoRepositoryMock.findByCpf(anyString()))
                .thenReturn(new Associado(ID2, NOME2, CPF2));

        EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
            Associado associadoAlterado = new Associado();
            associadoAlterado.setId(ID1);
            associadoAlterado.setNome(NOME3);
            associadoAlterado.setCpf(CPF2);
            associadoService.alterar(ID1, associadoAlterado);
        });

        assertTrue(thrown.getMessage().contains("CPF já existente na Base de Dados."));
    }

    @Test
    public void deveAlterarAssociadoComSucesso() {
        when(associadoRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(new Associado(ID1, NOME1, CPF1)));

        when(associadoRepositoryMock.findByCpf(anyString()))
                .thenReturn(new Associado(ID1, NOME1, CPF1));

        when(associadoRepositoryMock.save(any())).thenReturn(new Associado(ID1, NOME2, CPF2));

        Associado associadoAlterado = new Associado();
        associadoAlterado.setId(ID1);
        associadoAlterado.setNome(NOME2);
        associadoAlterado.setCpf(CPF2);
        Associado associadoDoBD = associadoService.alterar(ID1, associadoAlterado);

        assertEquals(associadoDoBD.getNome(), associadoAlterado.getNome());
    }

    @Test
    public void deveConsultarAssociadoComSucesso() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        when(entityManagerMock.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<Associado> cq = mock(CriteriaQuery.class);
        when(cb.createQuery(eq(Associado.class))).thenReturn(cq);

        Root<Associado> associadoRoot = mock(Root.class);
        when(cq.from(eq(Associado.class))).thenReturn(associadoRoot);


        TypedQuery<Associado> query = mock(TypedQuery.class);
        when(entityManagerMock.createQuery(any(CriteriaQuery.class))).thenReturn(query);

        associadoService.consultar(Optional.of("1"),
                Optional.of(NOME1));

        Assertions.assertTrue(true, "Consulta executada com sucesso.");
    }

    @Test
    public void deveConsultarAssociadoPorIdComSucesso() {
        Associado associado = new Associado();
        Optional<Associado> associadoOpt = Optional.of(associado);
        when(associadoRepositoryMock.findById(anyLong())).thenReturn(associadoOpt);

        Associado associadoOptBD = associadoService.consultarPorId(1L);

        Assertions.assertEquals(associadoOptBD, associado);
    }

    @Test
    public void deveExcluirAssociadoComSucesso() {
        Associado associado = new Associado();
        Optional<Associado> associadoOpt = Optional.of(associado);
        when(associadoRepositoryMock.findById(anyLong())).thenReturn(associadoOpt);

        doNothing().when(associadoRepositoryMock).deleteById(anyLong());
        associadoService.excluir(ID1);
        verify(associadoRepositoryMock, times(1)).deleteById(anyLong());

        Assertions.assertTrue(true, "Associado excluído.");
    }

    @Test
    public void deveFalharQuandoExcluirAssociadoInexistente() {
        when(associadoRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException thrown = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            associadoService.excluir(1L);
        });

        assertTrue(thrown.getMessage().contains("Objeto não encontrado."));
    }


}
