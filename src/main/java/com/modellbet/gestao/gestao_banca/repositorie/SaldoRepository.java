package com.modellbet.gestao.gestao_banca.repositorie;

import com.modellbet.gestao.gestao_banca.model.Saldo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaldoRepository extends JpaRepository<Saldo, Long> {
    Saldo findByUsuarioId(Long usuarioId);
}
