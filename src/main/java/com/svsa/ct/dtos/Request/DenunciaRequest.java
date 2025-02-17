package com.svsa.ct.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record DenunciaRequest(
                                @NotNull Date dataEmissao,
                                String relato,
                                String responsaveis,
                                String criancasAdolescentes,
                                String medidasAplicadas,
                                @NotBlank String origemDenuncia,
                                @NotBlank String statusRD
                              ) {
}
