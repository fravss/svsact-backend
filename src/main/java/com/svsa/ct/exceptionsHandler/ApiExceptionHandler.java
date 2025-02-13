package com.svsa.ct.exceptionsHandler;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.svsa.ct.exceptionsHandler.exceptions.EntidadeNaoEncontradaException;
import com.svsa.ct.exceptionsHandler.exceptions.SecretNaoEncontradoException;
import com.svsa.ct.exceptionsHandler.exceptions.TokenNaoEncontradoException;
import com.svsa.ct.exceptionsHandler.exceptions.UsuarioNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.util.List;
import java.util.stream.Collectors;



@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<?> JWTVerificationErrorHandler(JWTVerificationException exception, WebRequest request){
        if (exception instanceof TokenNaoEncontradoException
                || exception instanceof SecretNaoEncontradoException) {
            return handleExceptionInternal(exception, exception.getMessage(),  new HttpHeaders(),
                    HttpStatus.UNAUTHORIZED, request);
        }
        if (exception instanceof JWTDecodeException) {
            return handleExceptionInternal(exception, "O token está em formato inválido", new HttpHeaders(),
                    HttpStatus.UNAUTHORIZED, request);
        }
        return handleExceptionInternal(exception, "O token é inválido",  new HttpHeaders(),
                HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<?> InternalAuthenticationServiceErrorHandler(InternalAuthenticationServiceException ex, WebRequest request){
        if (ex.getCause() instanceof UsuarioNaoEncontradoException) {
           return EntidadeNaoEncontradaExceptionHandler((EntidadeNaoEncontradaException) ex.getCause(), request);
        }


        return handleExceptionInternal(ex, "Usuário ou senha inválido",  new HttpHeaders(),
                HttpStatus.UNAUTHORIZED, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }


        if (rootCause instanceof PropertyBindingException) {
            return  handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        return handleExceptionInternal(ex, "Body da requisição faltando ou inválido", headers, status, request);
    }

    public ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                 HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());
        String detail = String.format("A propriedade '%s' não existe.", path);
        return handleExceptionInternal(ex, detail, headers, status, request);
    }

    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());


        String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());



        return handleExceptionInternal(ex, detail, headers, status, request);
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
    public ResponseEntity<?> AutenticacaoErrorHandler(AuthenticationException exception, WebRequest request){
        return handleExceptionInternal(exception, "Usuário ou senha inválido",  new HttpHeaders(),
                HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> IllegalArgumentExceptionErrorHandler(IllegalArgumentException ex, WebRequest request){

        String classe = ex.getMessage().substring(ex.getMessage().lastIndexOf(" "), ex.getMessage().lastIndexOf("."));
        String valor = ex.getMessage().substring(ex.getMessage().lastIndexOf(".") + 1);

        String detail = String.format("O valor '%s' não é um valor válido para '%s'. ", valor , classe);

        return handleExceptionInternal(ex, detail ,  new HttpHeaders(),
                HttpStatus.UNAUTHORIZED, request);
    }



    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> ResponseStatusExceptionHandler(ResponseStatusException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getReason(),  new HttpHeaders(),
                exception.getStatusCode(), request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> EntidadeNaoEncontradaExceptionHandler(EntidadeNaoEncontradaException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getMessage(),  new HttpHeaders(),
              HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, "Ocorreu um erro inesperado", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.warn("Ocorreu um erro: {}", body, ex);
        if (body == null) {
            body = ExceptionMessage.builder()
                    .httpCode(statusCode.value())
                    .errorClass(ex.getClass().getSimpleName())
                    .message(ex.getMessage())
                    .build();
        } else if (body instanceof String) {
            body = ExceptionMessage.builder()
                    .httpCode(statusCode.value())
                    .errorClass(ex.getClass().getSimpleName())
                    .message((String) body)
                    .build();
        }


        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
    private ExceptionMessage.ExceptionMessageBuilder createProblemBuilder(HttpStatus status,
                                                        String errorClass, String message) {

        return ExceptionMessage.builder()
                .httpCode(status.value())
                .errorClass(errorClass)
                .message(message);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }
}

