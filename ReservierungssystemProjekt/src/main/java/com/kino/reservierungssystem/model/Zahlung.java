package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zahlung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double betrag;
    private LocalDate zahlungsdatum;
    private Long buchungId; // Zugehörige Buchungs-ID

    @Version // Optimistisches Locking
    private int version;

    public boolean verarbeiten() {
        return true; // Simulierte Online-Zahlung
    }
}