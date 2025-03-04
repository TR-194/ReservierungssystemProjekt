package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "kunde")
    private List<Reservierung> reservierungen;

    @OneToMany(mappedBy = "kunde")
    private List<Buchung> buchungen;

    // Getter & Setter
}
