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
@Data
@Builder
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titel;
    private String alterbeschraenkung;
    private int dauer;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auffuehrung> auffuehrungen = new ArrayList<>();

    /**
     * Fügt eine Aufführung zum Film hinzu und pflegt die bidirektionale Beziehung.
     *
     * @param auffuehrung die hinzuzufügende Aufführung
     */
    public void addAuffuehrung(Auffuehrung auffuehrung) {
        auffuehrungen.add(auffuehrung);
        auffuehrung.setFilm(this);
    }

    /**
     * Entfernt eine Aufführung vom Film und löst die Beziehung auf.
     *
     * @param auffuehrung die zu entfernende Aufführung
     */
    public void removeAuffuehrung(Auffuehrung auffuehrung) {
        auffuehrungen.remove(auffuehrung);
        auffuehrung.setFilm(null);
    }
}
