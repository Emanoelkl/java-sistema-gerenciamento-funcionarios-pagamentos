package com.emanoel.sistema.repository;

import com.emanoel.sistema.model.Funcionario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FuncionarioRepository {
    private final List<Funcionario> funcionarios = new ArrayList<>();

    public void salvar(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo.");
        }
        funcionarios.add(funcionario);
    }

    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios); // retorna uma cópia para proteger a lista original
    }

    // Sobrecarga 1: Busca por código
    public Optional<Funcionario> buscarFuncionario(int codigo) {
        return funcionarios.stream()
                .filter(f -> f.getCodigo() == codigo)
                .findFirst();
    }

    // Sobrecarga 2: Busca por CPF
    public Optional<Funcionario> buscarFuncionario(String cpf) {
        return funcionarios.stream()
                .filter(f -> f.getCpf().equals(cpf))
                .findFirst();
    }

    // Sobrecarga 3: Busca por nome (exata ou parcial)
    public List<Funcionario> buscarFuncionario(String nome, boolean buscaExata) {
        if (nome == null) {
            return new ArrayList<>();
        }
        return funcionarios.stream()
                .filter(f -> buscaExata
                        ? f.getNome().equalsIgnoreCase(nome)
                        : f.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean remover(int codigo) {
        return funcionarios.removeIf(f -> f.getCodigo() == codigo);
    }
}
