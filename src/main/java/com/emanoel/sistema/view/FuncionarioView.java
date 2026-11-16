package com.emanoel.sistema.view;

import com.emanoel.sistema.exception.FuncionarioJaCadastradoException;
import com.emanoel.sistema.exception.FuncionarioNaoEncontradoException;
import com.emanoel.sistema.model.*;
import com.emanoel.sistema.service.FuncionarioService;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class FuncionarioView {
    private final FuncionarioService service;
    private final Scanner scanner;

    public FuncionarioView(FuncionarioService service) {
        this.service = service;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    public void iniciar() {
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números válidos para a opção.");
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE FUNCIONÁRIOS ===");
        System.out.println("1. Cadastrar Funcionário");
        System.out.println("2. Listar Todos os Funcionários");
        System.out.println("3. Buscar Funcionário por Código");
        System.out.println("4. Exibir Dados Completos e Remuneração");
        System.out.println("5. Remover Funcionário");
        System.out.println("6. Gerar Relatório da Folha de Pagamento");
        System.out.println("7. Calcular Total da Folha de Pagamento");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void processarOpcao(int opcao) {
        try {
            switch (opcao) {
                case 1 -> cadastrarFuncionario();
                case 2 -> listarFuncionarios();
                case 3 -> buscarFuncionario();
                case 4 -> exibirDadosCompletos();
                case 5 -> removerFuncionario();
                case 6 -> System.out.println("\n" + service.gerarRelatorio());
                case 7 -> System.out.printf(Locale.US, "\nTotal da Folha de Pagamento: R$ %.2f\n", service.calcularTotalFolhaPagamento());
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } catch (FuncionarioJaCadastradoException | FuncionarioNaoEncontradoException | IllegalArgumentException e) {
            // Tratamento centralizado das exceções de regra de negócio e validação
            System.out.println("\n[AVISO]: " + e.getMessage());
        }
    }

    private void cadastrarFuncionario() {
        System.out.println("\n--- Tipo de Funcionário ---");
        System.out.println("1. CLT | 2. PJ | 3. Comissionado");
        System.out.print("Escolha o tipo: ");
        int tipo = Integer.parseInt(scanner.nextLine());

        System.out.print("Código identificador: ");
        int codigo = Integer.parseInt(scanner.nextLine());

        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();

        System.out.print("CPF (11 dígitos sem pontos): ");
        String cpf = scanner.nextLine();

        Funcionario novoFuncionario;

        switch (tipo) {
            case 1 -> {
                System.out.print("Salário-base: ");
                double salarioBase = Double.parseDouble(scanner.nextLine());
                System.out.print("Adicional de benefícios: ");
                double beneficios = Double.parseDouble(scanner.nextLine());
                novoFuncionario = new FuncionarioCLT(codigo, nome, cpf, salarioBase, beneficios);
            }
            case 2 -> {
                System.out.print("Valor por projeto: ");
                double valorProjeto = Double.parseDouble(scanner.nextLine());
                System.out.print("Quantidade de projetos: ");
                int qtdProjetos = Integer.parseInt(scanner.nextLine());
                novoFuncionario = new FuncionarioPJ(codigo, nome, cpf, valorProjeto, qtdProjetos);
            }
            case 3 -> {
                System.out.print("Salário-base: ");
                double salarioBase = Double.parseDouble(scanner.nextLine());
                System.out.print("Total de vendas no mês: ");
                double totalEmVendas = Double.parseDouble(scanner.nextLine());
                System.out.print("Percentual de comissão (ex: 0.05 para 5%): ");
                novoFuncionario = new FuncionarioComissionado(codigo, nome, cpf, salarioBase, totalEmVendas);
            }
            default -> throw new IllegalArgumentException("Tipo de funcionário inválido.");
        }

        service.cadastrar(novoFuncionario);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private void listarFuncionarios() {
        List<Funcionario> lista = service.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("\nNenhum funcionário cadastrado.");
            return;
        }
        System.out.println("\n--- Lista de Funcionários ---");
        for (Funcionario f : lista) {
            System.out.printf("Código: %d | Nome: %s | Categoria: %s\n",
                    f.getCodigo(), f.getNome(), f.getClass().getSimpleName());
        }
    }

    private void buscarFuncionario() {
        System.out.print("\nDigite o código do funcionário: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        Funcionario f = service.buscarPorCodigo(codigo);
        System.out.printf("Encontrado -> Código: %d | Nome: %s | CPF: %s\n", f.getCodigo(), f.getNome(), f.getCpf());
    }

    private void exibirDadosCompletos() {
        System.out.print("\nDigite o código do funcionário: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        Funcionario f = service.buscarPorCodigo(codigo);

        double tributos = (f instanceof Tributavel) ? ((Tributavel) f).calcularTributos() : 0.0;

        System.out.println("\n--- Dados Completos do Colaborador ---");
        System.out.println("Código: " + f.getCodigo());
        System.out.println("Nome: " + f.getNome());
        System.out.println("CPF: " + f.getCpf());
        System.out.println("Categoria: " + f.getClass().getSimpleName());
        System.out.printf(Locale.US, "Salário-Base: R$ %.2f\n", f.getSalarioBase());
        System.out.printf(Locale.US, "Remuneração Final: R$ %.2f\n", f.calcularRemuneracao());
        System.out.printf(Locale.US, "Tributos/Descontos: R$ %.2f\n", tributos);
    }

    private void removerFuncionario() {
        System.out.print("\nDigite o código do funcionário a remover: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        service.remover(codigo);
        System.out.println("Funcionário removido com sucesso!");
    }
}