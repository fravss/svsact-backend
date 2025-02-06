package com.svsa.ct.exceptionsHandler;

import com.svsa.ct.exceptionsHandler.exceptions.EntidadeNaoEncontradaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, "Body da requisição faltando ou inválido", headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        String body = String.join(", ", errors);

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<?> AutenticacaoErrorHandler(AuthenticationException exception, WebRequest request){
        return handleExceptionInternal(exception, "Usuário ou senha inválido",  new HttpHeaders(),
                HttpStatus.UNAUTHORIZED, request);
    }



    @ExceptionHandler(ResponseStatusException.class)
    private ResponseEntity<?> ResponseStatusExceptionHandler(ResponseStatusException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getReason(),  new HttpHeaders(),
                exception.getStatusCode(), request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    private ResponseEntity<?> EntidadeNaoEncontradaExceptionHandler(EntidadeNaoEncontradaException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getMessage(),  new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (body == null) {
            body = ExceptionMessage.builder()
                    .mensagem(ex.getMessage())
                    .build();
        } else if (body instanceof String) {
            body = ExceptionMessage.builder()
                    .mensagem((String) body)
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}

