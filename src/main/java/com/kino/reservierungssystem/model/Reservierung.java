package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservierung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate ablaufDatum;

    @ManyToOne
    @JoinColumn(name = "kunde_id")
    private Kunde kunde;

    @ManyToOne
    @JoinColumn(name = "auffuehrung_id")
    private Auffuehrung auffuehrung;

    @OneToMany(mappedBy = "reservierung")
    private List<Sitzplatz> sitzplaetze;


    public boolean istGueltig() {
        return LocalDate.now().isBefore(ablaufDatum);
    }

    // Getter & Setter
}
