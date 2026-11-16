package com.emanoel.sistema.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioComissionadoTest {
    @Test
    void deveCalcularRemuneracaoComComissaoCorretamente() {
        FuncionarioComissionado com = new FuncionarioComissionado(1, "Ana Silva", "12345678901", 2000.0, 10000.0);

        assertEquals(1, com.getCodigo());
        assertEquals("Ana Silva", com.getNome());
        assertEquals(3000.0, com.calcularRemuneracao());
    }

    @Test
    void deveCalcularTributosCorretamente() {
        // 10% sobre a remuneracao total
        FuncionarioComissionado com = new FuncionarioComissionado(1, "Ana Silva", "12345678901", 2000.0, 10000.0);

        assertEquals(300.0, com.calcularTributos());
    }

    @Test
    void deveLancarExcecaoQuandoDadosObrigatoriosForemInvalidos() {
        // Código inválido (zero ou negativo)
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioComissionado(0, "Ana", "12345678901", 2000.0, 10000.0));

        // Nome vazio ou nulo
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioComissionado(1, "", "12345678901", 2000.0, 10000.0));

        // CPF inválido (diferente de 11 dígitos numéricos)
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioComissionado(1, "Ana", "123", 2000.0, 10000.0));

        // Salário-base negativo
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioComissionado(1, "Ana", "12345678901", -100.0, 10000.0));

        // total em vendas negativo
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioComissionado(1, "Ana", "12345678901", 2000.0, -10000.0));
    }

}