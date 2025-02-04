package com.svsa.ct.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class AutenticacaoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ErrorMessage> AutenticacaoErrorHandler(AuthenticationException exception){
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ErrorMessage> CredenciaisInvalidasErrorHandler(BadCredentialsException exception){
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(),exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }



    @ExceptionHandler(JWTCreationException.class)
    private ResponseEntity<ErrorMessage> JWTErrorHandler(JWTCreationException exception){
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorMessage> JWTArgumentErrorHandler(IllegalArgumentException exception){
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(SignatureVerificationException.class)
    private ResponseEntity<ErrorMessage> JWTSignatureErrorHandler(SignatureVerificationException exception){
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(JWTVerificationException.class)
    private ResponseEntity<ErrorMessage> JWTVerificationErrorHandler(JWTVerificationException exception){
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.FORBIDDEN.value(), exception.getClass().getSimpleName(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

}

