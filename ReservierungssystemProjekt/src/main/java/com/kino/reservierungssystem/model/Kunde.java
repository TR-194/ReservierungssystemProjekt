package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kunde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "kunde", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservierung> reservierungen = new ArrayList<>();

    @OneToMany(mappedBy = "kunde", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Buchung> buchungen = new ArrayList<>();

    /**
     * Fügt eine Reservierung hinzu und pflegt die bidirektionale Beziehung.
     *
     * @param reservierung die hinzuzufügende Reservierung
     */
    public void addReservierung(Reservierung reservierung) {
        reservierungen.add(reservierung);
        reservierung.setKunde(this);
    }

    /**
     * Fügt eine Buchung hinzu und pflegt die bidirektionale Beziehung.
     *
     * @param buchung die hinzuzufügende Buchung
     */
    public void addBuchung(Buchung buchung) {
        buchungen.add(buchung);
        buchung.setKunde(this);
    }
}
