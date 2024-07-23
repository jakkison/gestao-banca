package com.modellbet.gestao.gestao_banca.service;

import com.modellbet.gestao.gestao_banca.model.Aposta;
import com.modellbet.gestao.gestao_banca.model.Saldo;
import com.modellbet.gestao.gestao_banca.repositorie.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SaldoService {
    @Autowired
    private SaldoRepository saldoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Saldo findByUsuarioId(Long usuarioId) {
        return saldoRepository.findByUsuarioId(usuarioId);
    }

    public void save(Saldo saldo) {
        saldoRepository.save(saldo);
    }

    public void updateSaldo(Aposta aposta) {
        Saldo saldo = findByUsuarioId(aposta.getUsuario().getId());
        BigDecimal novoSaldo = saldo.getBancaFinal();
        switch (aposta.getStatusAposta().toLowerCase()) {
            case "green":
            case "meio green":
                novoSaldo = novoSaldo.add(aposta.getRetornoAposta());
                break;
            case "red":
            case "meio red":
                novoSaldo = novoSaldo.subtract(aposta.getValor());
                break;
            case "devolvida":
            case "cashout":
                break;
            default:
                break;
        }
        saldo.setBancaFinal(novoSaldo);
        save(saldo);
    }

    public void updateSaldoComRetornoAnterior(Aposta aposta, BigDecimal oldRetornoAposta) {
        Long userId = aposta.getUsuario().getId();
        Saldo saldo = findByUsuarioId(userId);
        saldo.setBancaFinal(saldo.getBancaFinal().subtract(oldRetornoAposta).add(aposta.getRetornoAposta()));
        save(saldo);
    }
}

