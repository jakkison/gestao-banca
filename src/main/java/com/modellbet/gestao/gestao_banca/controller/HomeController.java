package com.modellbet.gestao.gestao_banca.controller;

import com.modellbet.gestao.gestao_banca.model.Usuario;
import com.modellbet.gestao.gestao_banca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioService.findByEmail(email);
        model.addAttribute("nomeUsuario", usuario.getNome());
        return "home";
    }
}
