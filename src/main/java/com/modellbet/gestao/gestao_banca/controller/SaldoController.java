package com.modellbet.gestao.gestao_banca.controller;

import com.modellbet.gestao.gestao_banca.model.Saldo;
import com.modellbet.gestao.gestao_banca.service.SaldoService;
import com.modellbet.gestao.gestao_banca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/saldo")
public class SaldoController {

    @Autowired
    private SaldoService saldoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String showSaldo(Model model, Principal principal) {
        Long userId = usuarioService.findByEmail(principal.getName()).getId();
        Saldo saldo = saldoService.findByUsuarioId(userId);
        if (saldo == null) {
            saldo = new Saldo();
            saldo.setUsuario(usuarioService.findByEmail(principal.getName()));
            saldo.setBancaInicial(BigDecimal.ZERO);
            saldo.setBancaFinal(BigDecimal.ZERO);
            saldoService.save(saldo);
        }
        model.addAttribute("saldo", saldo);
        return "saldo";
    }

    @PostMapping("/adicionar")
    public String addSaldo(@RequestParam("valor") BigDecimal valor, Principal principal) {
        Long userId = usuarioService.findByEmail(principal.getName()).getId();
        Saldo saldo = saldoService.findByUsuarioId(userId);
        if (saldo == null) {
            saldo = new Saldo();
            saldo.setUsuario(usuarioService.findByEmail(principal.getName()));
            saldo.setBancaInicial(BigDecimal.ZERO);
            saldo.setBancaFinal(BigDecimal.ZERO);
            saldoService.save(saldo);
        }
        if (saldo.getBancaInicial() == null) {
            saldo.setBancaInicial(BigDecimal.ZERO);
        }
        if (saldo.getBancaFinal() == null) {
            saldo.setBancaFinal(BigDecimal.ZERO);
        }
        saldo.setBancaInicial(saldo.getBancaInicial().add(valor));
        saldoService.save(saldo);
        return "redirect:/saldo";
    }
}