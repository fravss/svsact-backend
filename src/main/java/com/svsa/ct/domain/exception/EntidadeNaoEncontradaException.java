package com.svsa.ct.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends NegocioException {

  @Serial
  private static final long serialVersionUID = 1L;

  public EntidadeNaoEncontradaException(String mensagem) {
    super(mensagem);
  }
}
