package com.modellbet.gestao.gestao_banca.controller;

import com.modellbet.gestao.gestao_banca.model.Aposta;
import com.modellbet.gestao.gestao_banca.model.Saldo;
import com.modellbet.gestao.gestao_banca.service.ApostaService;
import com.modellbet.gestao.gestao_banca.service.SaldoService;
import com.modellbet.gestao.gestao_banca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apostas")
public class ApostaController {
    @Autowired
    private ApostaService apostaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SaldoService saldoService;

    @GetMapping("/nova")
    public String showNewApostaForm(Model model) {
        model.addAttribute("aposta", new Aposta());
        return "nova_aposta";
    }

    @PostMapping("/nova")
    public String placeAposta(@ModelAttribute Aposta aposta, Principal principal, Model model) {
        Long userId = usuarioService.findByEmail(principal.getName()).getId();
        Saldo saldo = saldoService.findByUsuarioId(userId);

        // Verificar se o saldo é suficiente
        if (saldo.getBancaFinal().compareTo(aposta.getValor()) < 0) {
            model.addAttribute("erro", "Saldo insuficiente. Seu saldo atual é: " + saldo.getBancaFinal());
            model.addAttribute("aposta", aposta);
            return "nova_aposta";
        }

        aposta.setUsuario(usuarioService.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado")));
        apostaService.save(aposta);

        // Atualizar o saldo
        saldo.setBancaFinal(saldo.getBancaFinal().subtract(aposta.getValor()));
        saldoService.save(saldo);

        return "redirect:/apostas";
    }

    @GetMapping
    public String showApostas(Model model, Principal principal) {
        Long userId = usuarioService.findByEmail(principal.getName()).getId();
        List<Aposta> apostas = apostaService.findByUsuarioId(userId);
        Saldo saldo = saldoService.findByUsuarioId(userId);
        model.addAttribute("apostas", apostas);
        model.addAttribute("saldo", saldo);
        return "lista_apostas";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Aposta> optionalAposta = apostaService.findById(id);
        if (optionalAposta.isPresent()) {
            model.addAttribute("aposta", optionalAposta.get());
            return "editar_aposta";
        } else {
            // Lidar com o caso em que a aposta não foi encontrada
            return "redirect:/apostas";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String updateAposta(@PathVariable("id") Long id, @ModelAttribute("aposta") Aposta aposta) {
        Optional<Aposta> optionalAposta = apostaService.findById(id);
        if (optionalAposta.isPresent()) {
            Aposta existingAposta = optionalAposta.get();
            existingAposta.setDataHora(aposta.getDataHora());
            existingAposta.setEvento(aposta.getEvento());
            existingAposta.setMercado(aposta.getMercado());
            existingAposta.setGrupo(aposta.getGrupo());
            existingAposta.setOdd(aposta.getOdd());
            existingAposta.setUnidade(aposta.getUnidade());
            existingAposta.setValor(aposta.getValor());
            existingAposta.setPercentInvestido(aposta.getPercentInvestido());
            existingAposta.setStatusAposta(aposta.getStatusAposta());
            existingAposta.setRetornoAposta(aposta.getRetornoAposta());
            apostaService.save(existingAposta);
        }
        return "redirect:/apostas";
    }

    @GetMapping("/deletar/{id}")
    public String deleteAposta(@PathVariable("id") Long id, Principal principal) {
        Long userId = usuarioService.findByEmail(principal.getName()).getId();
        Optional<Aposta> apostaOpt = apostaService.findById(id);
        if (apostaOpt.isPresent() && apostaOpt.get().getUsuario().getId().equals(userId)) {
            apostaService.deleteById(id);
        }
        return "redirect:/apostas";
    }

    @PostMapping("/atualizarStatus/{id}")
    public String updateApostaStatus(@PathVariable("id") Long id, @RequestParam("statusAposta") String statusAposta, @RequestParam(value = "retornoManual", required = false) BigDecimal retornoManual) {
        Optional<Aposta> optionalAposta = apostaService.findById(id);
        if (optionalAposta.isPresent()) {
            Aposta aposta = optionalAposta.get();
            BigDecimal oldRetornoAposta = aposta.getRetornoAposta();  // Guarda o retorno anterior

            aposta.setStatusAposta(statusAposta);

            BigDecimal newRetornoAposta = BigDecimal.ZERO;
            BigDecimal valor = aposta.getValor();
            BigDecimal odd = BigDecimal.valueOf(aposta.getOdd());

            switch (statusAposta) {
                case "Green":
                    newRetornoAposta = valor.multiply(odd);
                    break;
                case "Meio Green":
                    newRetornoAposta = valor.multiply(odd).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
                    break;
                case "Red":
                    newRetornoAposta = BigDecimal.ZERO;
                    break;
                case "Meio Red":
                    newRetornoAposta = valor.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
                    break;
                case "Devolvida":
                    newRetornoAposta = valor;
                    break;
                case "Cashout":
                    if (retornoManual != null) {
                        newRetornoAposta = retornoManual;
                    }
                    break;
                case "Pré Live":
                    newRetornoAposta = BigDecimal.ZERO;
                    break;
            }
            aposta.setRetornoAposta(newRetornoAposta);

            apostaService.save(aposta);
            saldoService.updateSaldoComRetornoAnterior(aposta, oldRetornoAposta);  // Use um método que considera o retorno anterior
        }
        return "redirect:/apostas";
    }



}
