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

    private double parkettPreis;
    private double logePreis;
    private double logeMitServicePreis;

    /**
     * Berechnet den Preis für einen Sitzplatz basierend auf der Kategorie.
     *
     * @param kategorie die Kategorie des Sitzplatzes
     * @return der zugehörige Preis
     */
    public double berechnePreis(Kategorie kategorie) {
        switch (kategorie) {
            case PARKETT:
                return parkettPreis;
            case LOGE:
                return logePreis;
            case LOGE_MIT_SERVICE:
                return logeMitServicePreis;
            default:
                return 0.0;
        }
    }
}
