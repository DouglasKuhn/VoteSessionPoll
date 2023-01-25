package br.com.votos.servico;

import br.com.votos.entidade.Associado;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssociadoServiceImpl implements AssociadoService{

    @Override
    public Associado criar (Associado associado) {
        return null;
    }

    @Override
    public Associado alterar (Long id, Associado associadoAlterado) {
        return null;
    }

    @Override
    public List<Associado> consultar(Long id, String nome, Boolean excluido) {
        return null;
    }

    @Override
    public Associado consultarPorId (Long id) {
        return null;
    }

    @Override
    public void excluir (Long id) {

    }
}
