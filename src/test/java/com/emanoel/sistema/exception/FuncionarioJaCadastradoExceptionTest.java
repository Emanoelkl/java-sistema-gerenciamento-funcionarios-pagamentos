package com.emanoel.sistema.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioJaCadastradoExceptionTest {
    @Test
    void deveCriarExcecaoComMensagemCorreta() {
        String mensagem = "Funcionário ja cadastrado";

        FuncionarioJaCadastradoException exception = assertThrows(
                FuncionarioJaCadastradoException.class,
                () -> { throw new FuncionarioJaCadastradoException(mensagem); }
        );

        assertEquals(mensagem, exception.getMessage());
    }
}