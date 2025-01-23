package com.svsa.ct.model;

import com.svsa.ct.model.enums.AgenteViolador;
import com.svsa.ct.model.enums.DireitoViolado;
import com.svsa.ct.model.enums.OrigemDenuncia;
import com.svsa.ct.model.enums.StatusRD;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "denuncia")
public class Denuncia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;
    private String relato;

    @ElementCollection(targetClass = DireitoViolado.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "DireitosViolados", joinColumns = @JoinColumn(name = "codigo_denuncia"))
    @Column(name = "direito", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<DireitoViolado> direitosViolados;

    @Enumerated(EnumType.STRING)
    private AgenteViolador agenteViolador;

    @Enumerated(EnumType.STRING)
    private OrigemDenuncia origemDenuncia;

    @Enumerated(EnumType.STRING)
    private StatusRD statusRD;

    @ManyToOne
    @JoinColumn(name = "conselheiro_id")
    private Usuario conselheiro;

    @ManyToOne
    @JoinColumn(name = "conselheiroref_id")
    private Usuario conselheiroReferencia;


    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;







}
