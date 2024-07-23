package com.modellbet.gestao.gestao_banca.repositorie;

import com.modellbet.gestao.gestao_banca.model.Aposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApostaRepository extends JpaRepository<Aposta, Long> {
    List<Aposta> findByUsuarioId(Long usuarioId);
}
