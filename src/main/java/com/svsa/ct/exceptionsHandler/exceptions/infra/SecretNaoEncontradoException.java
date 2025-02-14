package com.svsa.ct.exceptionsHandler.exceptions.infra;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class SecretNaoEncontradoException extends JWTVerificationException {
    public SecretNaoEncontradoException(String message) {
        super(message);
    }

    public SecretNaoEncontradoException() {
        super("A secret é nula");
    }
}
