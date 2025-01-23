package com.svsa.ct.dto;

import com.svsa.ct.model.enums.DireitoViolado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record DenunciaRecordDto(@NotNull Date dataEmissao,
                                @NotBlank String relato,
                                @NotBlank String origemDenuncia,
                                @NotBlank String statusRD,
                                @NotBlank String agenteViolador,
                                @NotNull  DireitoViolado[] direitosViolados) {
}
