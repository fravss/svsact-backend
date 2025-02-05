package com.svsa.ct.dtos.denunciaDtos;

import com.svsa.ct.dtos.usuarioDtos.UsuarioRecordDto;

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
