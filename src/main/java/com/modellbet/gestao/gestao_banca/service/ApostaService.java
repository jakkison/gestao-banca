package com.modellbet.gestao.gestao_banca.service;

import com.modellbet.gestao.gestao_banca.model.Aposta;
import com.modellbet.gestao.gestao_banca.repositorie.ApostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApostaService {
    @Autowired
    private ApostaRepository apostaRepository;

    public void save(Aposta aposta) {
        apostaRepository.save(aposta);
    }

    public List<Aposta> findByUsuarioId(Long usuarioId) {
        return apostaRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Aposta> findById(Long id) {
        return apostaRepository.findById(id);
    }

    public void deleteById(Long id) {
        apostaRepository.deleteById(id);
    }
}
