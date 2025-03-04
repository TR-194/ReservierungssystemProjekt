package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Buchung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kunde_id")
    private Kunde kunde;

    @ManyToOne
    @JoinColumn(name = "auffuehrung_id")
    private Auffuehrung auffuehrung;

    @OneToMany(mappedBy = "buchung")
    private List<Sitzplatz> sitzplaetze;

    @OneToOne(mappedBy = "buchung")
    private Zahlung zahlung;

    public void bezahlen() {
        // Logik für Zahlung
    }

    // Getter & Setter
}
