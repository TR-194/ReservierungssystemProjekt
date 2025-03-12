package com.kino.reservierungssystem.repository;

import com.kino.reservierungssystem.model.Buchung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {
    List<Buchung> findByKundeId(Long kundeId);

    @Query("SELECT SUM(b.preis) FROM Buchung b WHERE b.auffuehrung.id = :auffuehrungId")
    Double sumEinnahmenByAuffuehrung(Long auffuehrungId);

    @Query("SELECT SUM(b.preis) FROM Buchung b WHERE b.auffuehrung.film.id = :filmId")
    Double sumEinnahmenByFilm(Long filmId);
}