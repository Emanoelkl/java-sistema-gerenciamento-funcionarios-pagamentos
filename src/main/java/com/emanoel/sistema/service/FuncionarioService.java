package com.emanoel.sistema.service;

import com.emanoel.sistema.exception.FuncionarioJaCadastradoException;
import com.emanoel.sistema.exception.FuncionarioNaoEncontradoException;
import com.emanoel.sistema.model.Funcionario;
import com.emanoel.sistema.model.Tributavel;
import com.emanoel.sistema.repository.FuncionarioRepository;

import java.util.List;
import java.util.Locale;

public class FuncionarioService {
    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo.");
        }

        if (repository.buscarFuncionario(funcionario.getCodigo()).isPresent()) {
            throw new FuncionarioJaCadastradoException("Já existe um funcionário cadastrado com o código: " + funcionario.getCodigo());
        }

        if (repository.buscarFuncionario(funcionario.getCpf()).isPresent()) {
            throw new FuncionarioJaCadastradoException("Já existe um funcionário cadastrado com o CPF: " + funcionario.getCpf());
        }

        repository.salvar(funcionario);
    }

    public List<Funcionario> listarTodos() {
        return repository.listarTodos();
    }

    public Funcionario buscarPorCodigo(int codigo) {
        return repository.buscarFuncionario(codigo)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado com o código: " + codigo));
    }

    public Funcionario buscarPorCpf(String cpf) {
        return repository.buscarFuncionario(cpf)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado com o CPF: " + cpf));
    }

    public List<Funcionario> buscarPorNome(String nome, boolean buscaExata) {
        List<Funcionario> encontrados = repository.buscarFuncionario(nome, buscaExata);
        if (encontrados.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Nenhum funcionário encontrado com o nome: " + nome);
        }
        return encontrados;
    }

    public void remover(int codigo) {
        boolean removido = repository.remover(codigo);
        if (!removido) {
            throw new FuncionarioNaoEncontradoException("Não foi possível remover. Funcionário inexistente com código: " + codigo);
        }
    }

    public double calcularTotalFolhaPagamento() {
        return repository.listarTodos().stream()
                .mapToDouble(Funcionario::calcularRemuneracao)
                .sum();
    }

    public String gerarRelatorio() {
        List<Funcionario> todos = repository.listarTodos();
        if (todos.isEmpty()) {
            return "Nenhum funcionário cadastrado no sistema.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DA FOLHA DE PAGAMENTO ===\n");

        for (Funcionario f : todos) {
            double remuneracao = f.calcularRemuneracao();
            double tributos = (f instanceof Tributavel) ? ((Tributavel) f).calcularTributos() : 0.0;

            sb.append(String.format(Locale.US,
                    "Código: %d | Nome: %s | CPF: %s | Tipo: %s | Remuneração: R$ %.2f | Tributos: R$ %.2f\n",
                    f.getCodigo(),
                    f.getNome(),
                    f.getCpf(),
                    f.getClass().getSimpleName(),
                    remuneracao,
                    tributos
            ));
        }

        sb.append(String.format(Locale.US, "TOTAL DA FOLHA: R$ %.2f\n", calcularTotalFolhaPagamento()));
        return sb.toString();
    }
}
