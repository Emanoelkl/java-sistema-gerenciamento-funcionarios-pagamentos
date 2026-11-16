package com.emanoel.sistema.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioCLTTest {
    @Test
    void deveCalcularRemuneracaoComBeneficiosCorretamente() {
        FuncionarioCLT clt = new FuncionarioCLT(1, "Ana Silva", "12345678901", 3000.0, 500.0);

        assertEquals(1, clt.getCodigo());
        assertEquals("Ana Silva", clt.getNome());
        assertEquals(3500.0, clt.calcularRemuneracao());
    }

    @Test
    void deveCalcularTributosCorretamente() {
        // Exemplo de regra: 10% sobre o salário-base
        FuncionarioCLT clt = new FuncionarioCLT(1, "Ana Silva", "12345678901", 3000.0, 500.0);

        assertEquals(300.0, clt.calcularTributos());
    }

    @Test
    void deveLancarExcecaoQuandoDadosObrigatoriosForemInvalidos() {
        // Código inválido (zero ou negativo)
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioCLT(0, "Ana", "12345678901", 3000.0, 500.0));

        // Nome vazio ou nulo
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioCLT(1, "", "12345678901", 3000.0, 500.0));

        // CPF inválido (diferente de 11 dígitos numéricos)
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioCLT(1, "Ana", "123", 3000.0, 500.0));

        // Salário-base negativo
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioCLT(1, "Ana", "12345678901", -100.0, 500.0));

        // Benefício negativo
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioCLT(1, "Ana", "12345678901", 3000.0, -50.0));
    }
}