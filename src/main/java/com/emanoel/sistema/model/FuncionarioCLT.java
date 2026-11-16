package com.emanoel.sistema.model;

public class FuncionarioCLT extends Funcionario implements Tributavel {
    private double adicionalBeneficios;

    public FuncionarioCLT(int codigo, String nome, String cpf, double salarioBase, double adicionalBeneficios) {
        super(codigo, nome, cpf, salarioBase);
        setAdicionalBeneficios(adicionalBeneficios);
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + adicionalBeneficios;
    }

    @Override
    public double calcularTributos() {
        return getSalarioBase() * 0.10; // 10% de desconto/tributo
    }

    public double getAdicionalBeneficios() {
        return adicionalBeneficios;
    }

    public void setAdicionalBeneficios(double adicionalBeneficios) {
        if (adicionalBeneficios < 0) {
            throw new IllegalArgumentException("Adicional de benefícios não pode ser negativo.");
        }
        this.adicionalBeneficios = adicionalBeneficios;
    }
}
