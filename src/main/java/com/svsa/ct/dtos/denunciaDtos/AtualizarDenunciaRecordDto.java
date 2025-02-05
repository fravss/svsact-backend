package com.svsa.ct.dtos.denunciaDtos;

import java.util.Date;

public record AtualizarDenunciaRecordDto(
        Date dataEmissao,
        String relato,
        String responsaveis,
        String criancasAdolescentes,
        String medidasAplicadas,
        String origemDenuncia,
        String statusRD
) {
}
