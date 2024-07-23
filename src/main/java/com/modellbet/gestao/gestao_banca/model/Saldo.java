package com.modellbet.gestao.gestao_banca.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Saldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal bancaInicial;
    private BigDecimal bancaFinal;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBancaInicial() {
        return bancaInicial;
    }

    public void setBancaInicial(BigDecimal bancaInicial) {
        this.bancaInicial = bancaInicial;
    }

    public BigDecimal getBancaFinal() {
        return bancaFinal;
    }

    public void setBancaFinal(BigDecimal bancaFinal) {
        this.bancaFinal = bancaFinal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

