package com.kino.reservierungssystem.repository;

import com.kino.reservierungssystem.model.Auffuehrung;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuffuehrungRepository extends JpaRepository<Auffuehrung, Long> {
    List<Auffuehrung> findByFilmId(Long filmId);
}