package com.emanoel.sistema.model;

public class FuncionarioPJ extends Funcionario implements Tributavel {
    private int qtdProjetos;

    public FuncionarioPJ(int codigo, String nome, String cpf, double salarioBase, int qtdProjetos) {
        super(codigo, nome, cpf, salarioBase);
        setQtdProjetos(qtdProjetos);
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() * qtdProjetos;
    }

    @Override
    public double calcularTributos() {
        return calcularRemuneracao() * 0.05; // 5% de desconto/tributo
    }

    public double getQtdProjetos() {
        return qtdProjetos;
    }

    public void setQtdProjetos(int qtdProjetos) {
        if (qtdProjetos < 0) {
            throw new IllegalArgumentException("quantidade de projetos não pode ser negativo.");
        }
        this.qtdProjetos = qtdProjetos;
    }
}
