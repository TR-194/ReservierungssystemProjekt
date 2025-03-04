package com.kino.reservierungssystem.repository;

import com.kino.reservierungssystem.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
