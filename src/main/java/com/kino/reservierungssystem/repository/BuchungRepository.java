package com.kino.reservierungssystem.repository;

import com.kino.reservierungssystem.model.Buchung;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {
    List<Buchung> findByKundeId(Long kundeId);
}