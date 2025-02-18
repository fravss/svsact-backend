package com.svsa.ct.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioModel {
    private Long id;
    private String nome;
    private String email;
    private String nomeUnidade;
}
