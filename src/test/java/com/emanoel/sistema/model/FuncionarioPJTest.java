package com.emanoel.sistema.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioPJTest {
    @Test
    void deveCalcularRemuneracaoComBeneficiosCorretamente() {
        FuncionarioPJ pj = new FuncionarioPJ(1, "Ana Silva", "12345678901", 500.0, 3);

        assertEquals(1, pj.getCodigo());
        assertEquals("Ana Silva", pj.getNome());
        assertEquals(1500.0, pj.calcularRemuneracao());
    }

    @Test
    void deveCalcularTributosCorretamente() {
        //  5% sobre a remuneracao total
        FuncionarioPJ pj = new FuncionarioPJ(1, "Ana Silva", "12345678901", 500.0, 3);

        assertEquals(75.0, pj.calcularTributos());
    }

    @Test
    void deveLancarExcecaoQuandoDadosObrigatoriosForemInvalidos() {
        // Código inválido (zero ou negativo)
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioPJ(0, "Ana", "12345678901", 1500.0, 3));

        // Nome vazio ou nulo
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioPJ(1, "", "12345678901", 1500.0, 3));

        // CPF inválido (diferente de 11 dígitos numéricos)
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioPJ(1, "Ana", "123", 1500.0, 3));

        // Salário-base negativo
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioPJ(1, "Ana", "12345678901", -100.0, 3));

        // Quantidade de projetos negativo
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioPJ(1, "Ana", "12345678901", 500.0, -3));
    }

}