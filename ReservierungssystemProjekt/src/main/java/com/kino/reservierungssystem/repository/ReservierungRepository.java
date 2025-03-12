package com.kino.reservierungssystem.repository;

import com.kino.reservierungssystem.model.Reservierung;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservierungRepository extends JpaRepository<Reservierung, Long> {
    List<Reservierung> findByKundeId(Long kundeId);

    List<Reservierung> findByEmail(String email);
}