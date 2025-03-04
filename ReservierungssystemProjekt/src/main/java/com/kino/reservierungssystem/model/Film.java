package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titel;
    private String alterbeschraenkung;
    private int dauer;

    @OneToMany(mappedBy = "film")
    private List<Auffuehrung> auffuehrungen;

    // Getter & Setter
}
