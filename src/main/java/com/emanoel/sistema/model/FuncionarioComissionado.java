package com.emanoel.sistema.model;

public class FuncionarioComissionado extends Funcionario implements Tributavel {
    private double totalEmVendas;

    public FuncionarioComissionado(int codigo, String nome, String cpf, double salarioBase, double totalEmVendas) {
        super(codigo, nome, cpf, salarioBase);
        setTotalEmVendas(totalEmVendas);
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + (totalEmVendas * 0.1);
    }

    @Override
    public double calcularTributos() {
        return calcularRemuneracao() * 0.10; // 10% de desconto/tributo
    }

    public double getTotalEmVendas() {
        return totalEmVendas;
    }

    public void setTotalEmVendas(double totalEmVendas) {
        if (totalEmVendas < 0) {
            throw new IllegalArgumentException("total em vendas não pode ser negativo.");
        }
        this.totalEmVendas = totalEmVendas;
    }
}
