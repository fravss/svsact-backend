package com.svsa.ct.domain.model;

import com.svsa.ct.domain.model.enums.OrigemDenuncia;
import com.svsa.ct.domain.model.enums.StatusRD;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "denuncia")
public class Denuncia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "conselheiro_id")
    private Usuario conselheiro;


    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;

    private String relato;

    private String responsaveis;

    private String criancasAdolescentes;

    private String medidasAplicadas;

    @Enumerated(EnumType.STRING)
    private OrigemDenuncia origemDenuncia;

    @Enumerated(EnumType.STRING)
    private StatusRD statusRD;









}
