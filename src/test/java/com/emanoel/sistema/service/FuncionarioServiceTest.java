package com.emanoel.sistema.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.emanoel.sistema.exception.FuncionarioJaCadastradoException;
import com.emanoel.sistema.exception.FuncionarioNaoEncontradoException;
import com.emanoel.sistema.model.Funcionario;
import com.emanoel.sistema.model.FuncionarioCLT;
import com.emanoel.sistema.model.FuncionarioComissionado;
import com.emanoel.sistema.model.FuncionarioPJ;
import com.emanoel.sistema.repository.FuncionarioRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioServiceTest {

    private FuncionarioService service;
    private Funcionario clt;
    private Funcionario pj;

    @BeforeEach
    void setUp() {
        FuncionarioRepository repository = new FuncionarioRepository();
        service = new FuncionarioService(repository);

        clt = new FuncionarioCLT(1, "Mariana Lima", "11111111111", 3000.0, 500.0);
        pj = new FuncionarioPJ(2, "Pedro Alves", "22222222222", 500.0, 3);
    }

    @Test
    void deveCadastrarFuncionarioComSucesso() {
        service.cadastrar(clt);

        List<Funcionario> lista = service.listarTodos();
        assertEquals(1, lista.size());
        assertEquals("Mariana Lima", lista.get(0).getNome());
    }

    @Test
    void deveLancarExcecaoAoCadastrarCodigoDuplicado() {
        service.cadastrar(clt);
        Funcionario outroComMesmoCodigo = new FuncionarioPJ(1, "Outro", "99999999999", 500.0, 3);

        assertThrows(FuncionarioJaCadastradoException.class, () -> service.cadastrar(outroComMesmoCodigo));
    }

    @Test
    void deveLancarExcecaoAoCadastrarCpfDuplicado() {
        service.cadastrar(clt);
        Funcionario outroComMesmoCpf = new FuncionarioPJ(99, "Outro", "11111111111", 500.0, 3);

        assertThrows(FuncionarioJaCadastradoException.class, () -> service.cadastrar(outroComMesmoCpf));
    }

    @Test
    void deveBuscarFuncionarioPorCodigoComSucesso() {
        service.cadastrar(clt);

        Funcionario encontrado = service.buscarPorCodigo(1);
        assertEquals("Mariana Lima", encontrado.getNome());
    }

    @Test
    void deveLancarExcecaoAoBuscarCodigoInexistente() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarPorCodigo(999));
    }

    @Test
    void deveRemoverFuncionarioComSucesso() {
        service.cadastrar(clt);
        service.remover(1);

        assertEquals(0, service.listarTodos().size());
    }

    @Test
    void deveLancarExcecaoAoRemoverFuncionarioInexistente() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.remover(999));
    }

    @Test
    void deveCalcularTotalDaFolhaDePagamentoCorrectamente() {
        Funcionario comissionado = new FuncionarioComissionado(3, "Lucas", "33333333333", 2000.0, 10000.0);

        service.cadastrar(clt);
        service.cadastrar(pj);
        service.cadastrar(comissionado);

        double totalFolha = service.calcularTotalFolhaPagamento();
        assertEquals(8000.0, totalFolha);
    }

    @Test
    void deveGerarRelatorioContendoDadosDosFuncionarios() {
        service.cadastrar(clt);
        service.cadastrar(pj);

        String relatorio = service.gerarRelatorio();

        assertTrue(relatorio.contains("Mariana Lima"));
        assertTrue(relatorio.contains("3500.00") || relatorio.contains("3500,00"));
        assertTrue(relatorio.contains("Pedro Alves"));
        assertTrue(relatorio.contains("1500.00") || relatorio.contains("1500,00"));
    }
}