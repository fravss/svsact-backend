package com.svsa.ct.api.dtos;

import com.svsa.ct.domain.model.enums.OrigemDenuncia;
import com.svsa.ct.domain.model.enums.StatusRD;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DenunciaModel {

    private long id;


    private UsuarioModel Conselheiro;

    private Date dataEmissao;

    private String relato;

    private String responsaveis;

    private String criancasAdolescentes;

    private String medidasAplicadas;

    private OrigemDenuncia origemDenuncia;


    private StatusRD statusRD;

}
