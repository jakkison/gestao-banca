package com.modellbet.gestao.gestao_banca.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Aposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataHora;
    private String evento;
    private String mercado;
    private String grupo;
    private double odd;
    private double unidade;
    private BigDecimal valor;
    private double percentInvestido;
    private String statusAposta;
    private BigDecimal retornoAposta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public String getMercado() {
        return mercado;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public double getUnidade() {
        return unidade;
    }

    public void setUnidade(double unidade) {
        this.unidade = unidade;
    }

    public double getPercentInvestido() {
        return percentInvestido;
    }

    public void setPercentInvestido(double percentInvestido) {
        this.percentInvestido = percentInvestido;
    }

    public String getStatusAposta() {
        return statusAposta;
    }

    public void setStatusAposta(String statusAposta) {
        this.statusAposta = statusAposta;
    }

    public BigDecimal getRetornoAposta() {
        return retornoAposta;
    }

    public void setRetornoAposta(BigDecimal retornoAposta) {
        this.retornoAposta = retornoAposta;
    }
}
