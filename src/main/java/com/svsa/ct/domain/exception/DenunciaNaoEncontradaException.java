package com.svsa.ct.domain.exception;

import java.io.Serial;


public class DenunciaNaoEncontradaException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DenunciaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public DenunciaNaoEncontradaException(Long estadoId) {
        this(String.format("Não existe uma denuncia com código %d", estadoId));
    }
}
