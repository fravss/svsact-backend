package com.svsa.ct.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.svsa.ct.dtos.errorsDtos.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


@ControllerAdvice
public class AutenticacaoExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ErrorMessageDto> AutenticacaoErrorHandler(AuthenticationException exception){
        ErrorMessageDto threatResponse = new ErrorMessageDto(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), List.of(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ErrorMessageDto> CredenciaisInvalidasErrorHandler(BadCredentialsException exception){
        ErrorMessageDto threatResponse = new ErrorMessageDto(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), List.of(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }



    @ExceptionHandler(JWTCreationException.class)
    private ResponseEntity<ErrorMessageDto> JWTErrorHandler(JWTCreationException exception){
        ErrorMessageDto threatResponse = new ErrorMessageDto(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), List.of(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorMessageDto> JWTArgumentErrorHandler(IllegalArgumentException exception){
        ErrorMessageDto threatResponse = new ErrorMessageDto(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), List.of(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(SignatureVerificationException.class)
    private ResponseEntity<ErrorMessageDto> JWTSignatureErrorHandler(SignatureVerificationException exception){
        ErrorMessageDto threatResponse = new ErrorMessageDto(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), List.of(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(JWTVerificationException.class)
    private ResponseEntity<ErrorMessageDto> JWTVerificationErrorHandler(JWTVerificationException exception){
        ErrorMessageDto threatResponse = new ErrorMessageDto(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), List.of(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

}

