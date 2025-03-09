package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.concurrent.locks.ReentrantLock;

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

    // Transienter Lock zur Steuerung von Konkurrenzzugriffen – wird nicht in die DB geschrieben
    @Transient
    private final ReentrantLock lock = new ReentrantLock();

    // Getter für den Lock
    public ReentrantLock getLock() {
        return lock;
    }
}
