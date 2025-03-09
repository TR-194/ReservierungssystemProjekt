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

    private double logeMitServicePreis;
    private double logePreis;
    private double parkettPreis;

    public double berechnePreis(Kategorie kategorie) {
        switch (kategorie) {
            case LOGE_MIT_SERVICE:
                return logeMitServicePreis;
            case LOGE:
                return logePreis;
            case PARKETT:
                return parkettPreis;
            default:
                throw new IllegalArgumentException("Unbekannte Kategorie: " + kategorie);
        }
    }
}
