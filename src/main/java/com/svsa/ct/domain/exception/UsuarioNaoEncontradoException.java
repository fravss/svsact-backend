package com.svsa.ct.domain.exception;

import java.io.Serial;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this(String.format("Não existe um usuario com código %d", id));
    }
}
