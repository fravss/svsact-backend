package com.svsa.ct.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nome;
    private String rg;
    private Date dataNascimento;
    private String telefone;
    private Long numero;
    private String bairro;
    private String cep;
    private String municipio;
    private String estado;





}
