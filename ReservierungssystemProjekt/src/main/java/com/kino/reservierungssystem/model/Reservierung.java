package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservierung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "auffuehrung_id", nullable = false)
    private Auffuehrung auffuehrung;

    @ElementCollection
    private List<Long> sitzplatzIds; // Sitzplätze als ID-Referenzen

    private String status; // "Reserviert", "Gebucht"

    /**
     * Wandelt eine Reservierung in eine Buchung um.
     *
     * @return eine neue Buchung basierend auf dieser Reservierung.
     */
    public Buchung inBuchungUmwandeln() {
        Buchung buchung = new Buchung();
        buchung.setName(this.name);
        buchung.setEmail(this.email);
        buchung.setAuffuehrung(this.auffuehrung);
        buchung.setSitzplatzIds(this.sitzplatzIds);
        buchung.setStatus("Gebucht"); // Setzt den Status auf "Gebucht"
        return buchung;
    }
}