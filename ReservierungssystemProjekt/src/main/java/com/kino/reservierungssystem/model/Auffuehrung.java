package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auffuehrung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datum;
    private LocalTime uhrzeit;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "kinosaal_id")
    private Kinosaal kinosaal;

    @OneToMany(mappedBy = "auffuehrung")
    private List<Reservierung> reservierungen;

    @OneToMany(mappedBy = "auffuehrung")
    private List<Buchung> buchungen;


    // Getter & Setter
}
