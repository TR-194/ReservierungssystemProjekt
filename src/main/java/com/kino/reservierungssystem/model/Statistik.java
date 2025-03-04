package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double einnahmen;

    public double berechneEinnahmenProAuffuehrung(Auffuehrung auffuehrung) {
        return auffuehrung.getBuchungen().stream()
                .mapToDouble(buchung -> buchung.getZahlung().getBetrag())
                .sum();
    }

    public double berechneEinnahmenProFilm(Film film) {
        return film.getAuffuehrungen().stream()
                .mapToDouble(this::berechneEinnahmenProAuffuehrung)
                .sum();
    }

    // Getter & Setter
}
