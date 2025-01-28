package com.svsa.ct.dto.DenunciaDtos;

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
