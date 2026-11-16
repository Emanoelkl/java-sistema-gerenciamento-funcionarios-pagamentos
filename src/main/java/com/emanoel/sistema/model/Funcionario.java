package com.emanoel.sistema.model;

public abstract class Funcionario implements Identificavel {
    private int codigo;
    private String nome;
    private String cpf;
    private double salarioBase;

    public Funcionario(int codigo, String nome, String cpf, double salarioBase) {
        setCodigo(codigo);
        setNome(nome);
        setCpf(cpf);
        setSalarioBase(salarioBase);
    }

    public abstract double calcularRemuneracao();

    @Override
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("Código deve ser maior que zero.");
        }
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos numéricos.");
        }
        this.cpf = cpf;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("Salário-base não pode ser negativo.");
        }
        this.salarioBase = salarioBase;
    }
}
