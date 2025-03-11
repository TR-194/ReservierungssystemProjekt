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
    private Status status; // Enum for "frei", "reserviert", "gebucht"

    private Long auffuehrungId;  // Reference to Aufführung
    private Long kategorieId; // Reference to Sitzkategorie

    @Version // Optimistic Locking
    private int version;

    @Transient
    private final ReentrantLock lock = new ReentrantLock();
}
