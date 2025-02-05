package com.svsa.ct.exceptions.global;

import com.svsa.ct.dtos.errorsDtos.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


@ControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorMessageDto response = new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), exception.getClass().getSimpleName(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageDto> handleMessageNotReadableException(HttpMessageNotReadableException exception) {
        ErrorMessageDto response = new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), exception.getClass().getSimpleName(), List.of("Body da requisição faltando ou inválido"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



}
