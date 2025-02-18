package com.svsa.ct.api.dtos.Request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AutenticacaoRequest {
    @NotNull
    private String email;

    @NotNull
    private String senha;
}
