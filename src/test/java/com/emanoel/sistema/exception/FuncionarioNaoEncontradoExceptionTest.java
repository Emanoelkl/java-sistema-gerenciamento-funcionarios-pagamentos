package com.emanoel.sistema.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioNaoEncontradoExceptionTest {
    @Test
    void deveCriarExcecaoComMensagemCorreta() {
        String mensagem = "Funcionário não encontrado";

        FuncionarioNaoEncontradoException exception = assertThrows(
                FuncionarioNaoEncontradoException.class,
                () -> { throw new FuncionarioNaoEncontradoException(mensagem); }
        );

        assertEquals(mensagem, exception.getMessage());
    }
}