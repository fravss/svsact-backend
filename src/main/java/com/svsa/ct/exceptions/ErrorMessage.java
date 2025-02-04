package com.svsa.ct.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private int status;
    private String error;
    private String message;

}