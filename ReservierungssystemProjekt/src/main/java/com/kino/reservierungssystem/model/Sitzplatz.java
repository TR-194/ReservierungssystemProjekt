package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sitzplatz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int platzNr;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "sitzreihe_id")
    private Sitzreihe sitzreihe;

    @ManyToOne
    private Reservierung reservierung;

    @ManyToOne
    private Buchung buchung;

    // Getter & Setter
}
