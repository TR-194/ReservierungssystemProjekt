package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preismodell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long auffuehrungId; // Entkoppelt von @OneToOne, Kafka arbeitet mit IDs

    private double parkettPreis;
    private double logePreis;
    private double logeMitServicePreis;

    @Version // Optimistisches Locking für parallele Verarbeitung
    private int version;

    /**
     * Berechnet den Preis für einen Sitzplatz basierend auf der Kategorie.
     *
     * @param kategorie die Kategorie des Sitzplatzes (PARKETT, LOGE, LOGE_MIT_SERVICE)
     * @return der zugehörige Preis
     */
    public double berechnePreis(Kategorie kategorie) {
        return switch (kategorie) {
            case PARKETT -> parkettPreis;
            case LOGE -> logePreis;
            case LOGE_MIT_SERVICE -> logeMitServicePreis;
            default -> 0.0;
        };
    }
}
