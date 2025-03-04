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

    // Getter & Setter
}
