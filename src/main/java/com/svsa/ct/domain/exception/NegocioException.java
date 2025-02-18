package com.svsa.ct.domain.exception;



import java.io.Serial;


public class NegocioException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NegocioException(String message) {
        super(message);
    }
}
