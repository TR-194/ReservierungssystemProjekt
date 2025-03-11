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
public class Sitzreihe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int reihenNr;

    @Enumerated(EnumType.STRING)
    private Kategorie kategorie;

    @ManyToOne
    @JoinColumn(name = "kinosaal_id")
    private Kinosaal kinosaal;

    @OneToMany(mappedBy = "sitzreihe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sitzplatz> sitzplaetze = new ArrayList<>();


}
