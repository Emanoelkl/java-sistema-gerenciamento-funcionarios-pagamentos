package com.emanoel.sistema.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.emanoel.sistema.model.Funcionario;
import com.emanoel.sistema.model.FuncionarioCLT;
import com.emanoel.sistema.model.FuncionarioPJ;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioRepositoryTest {

    private FuncionarioRepository repository;
    private Funcionario func1;
    private Funcionario func2;

    @BeforeEach
    void setUp() {
        repository = new FuncionarioRepository();
        func1 = new FuncionarioCLT(1, "Carlos Souza", "11111111111", 3000.0, 400.0);
        func2 = new FuncionarioPJ(2, "Carlos Alberto", "22222222222", 500.0, 3);
    }

    @Test
    void deveSalvarEListarTodosOsFuncionarios() {
        repository.salvar(func1);
        repository.salvar(func2);

        List<Funcionario> todos = repository.listarTodos();
        assertEquals(2, todos.size());
    }

    @Test
    void deveBuscarFuncionarioPorCodigo() {
        repository.salvar(func1);

        Optional<Funcionario> encontrado = repository.buscarFuncionario(1);
        assertTrue(encontrado.isPresent());
        assertEquals("Carlos Souza", encontrado.get().getNome());

        Optional<Funcionario> naoEncontrado = repository.buscarFuncionario(99);
        assertTrue(naoEncontrado.isEmpty());
    }

    @Test
    void deveBuscarFuncionarioPorCpf() {
        repository.salvar(func1);

        Optional<Funcionario> encontrado = repository.buscarFuncionario("11111111111");
        assertTrue(encontrado.isPresent());
        assertEquals(1, encontrado.get().getCodigo());
    }

    @Test
    void deveBuscarFuncionarioPorNomeComSobrecargaExataEParcial() {
        repository.salvar(func1);
        repository.salvar(func2);

        // Busca exata (true) -> deve achar apenas "Carlos Souza"
        List<Funcionario> exata = repository.buscarFuncionario("Carlos Souza", true);
        assertEquals(1, exata.size());
        assertEquals(1, exata.get(0).getCodigo());

        // Busca parcial (false) -> deve achar os dois que contêm "Carlos"
        List<Funcionario> parcial = repository.buscarFuncionario("Carlos", false);
        assertEquals(2, parcial.size());
    }

    @Test
    void deveRemoverFuncionarioExistente() {
        repository.salvar(func1);

        boolean removido = repository.remover(1);
        assertTrue(removido);
        assertEquals(0, repository.listarTodos().size());
    }

    @Test
    void deveRetornarFalsoAoTentarRemoverFuncionarioInexistente() {
        boolean removido = repository.remover(999);
        assertFalse(removido);
    }
}