package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

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

    @OneToOne
    @JoinColumn(name = "buchung_id")
    private Buchung buchung;

    public boolean verarbeiten() {
        // Logik für Zahlungsvalidierung
        return true;
    }

}
