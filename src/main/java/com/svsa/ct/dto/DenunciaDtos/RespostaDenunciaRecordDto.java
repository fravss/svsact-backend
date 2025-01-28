package com.svsa.ct.dto.DenunciaDtos;

import com.svsa.ct.dto.UsuarioDtos.UsuarioRecordDto;

import java.util.Date;

public record RespostaDenunciaRecordDto(
        Long id,
        UsuarioRecordDto conselheiro,
        Date dataEmissao,
        String relato,
        String responsaveis,
        String criancasAdolescentes,
        String medidasAplicadas,
        String origemDenuncia,
        String statusRD) {
}
