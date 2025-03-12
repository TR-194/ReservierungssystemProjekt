package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Buchung {

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

    private String status; // "Gebucht"
}
