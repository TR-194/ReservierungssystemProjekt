package com.kino.reservierungssystem.repository;

import com.kino.reservierungssystem.model.Zahlung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZahlungRepository extends JpaRepository<Zahlung, Long> {
    Zahlung findByBuchungId(Long buchungId);
}