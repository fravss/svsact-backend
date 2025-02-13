package com.svsa.ct.exceptionsHandler.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class TokenNaoEncontradoException extends JWTVerificationException {
    public TokenNaoEncontradoException(String message) {
        super(message);
    }

    public TokenNaoEncontradoException() {

        super("Token não encontrado na requisição");
    }
}
