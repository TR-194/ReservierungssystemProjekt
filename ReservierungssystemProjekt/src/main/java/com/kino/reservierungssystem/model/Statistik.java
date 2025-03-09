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

    // In diesem Szenario werden die Statistiken über MongoDB berechnet.
    // Deshalb enthält diese Klasse keine Berechnungsmethoden mehr.
    private double einnahmen;
}
