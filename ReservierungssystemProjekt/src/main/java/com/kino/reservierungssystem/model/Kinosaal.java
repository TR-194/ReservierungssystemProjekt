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
public class Kinosaal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean freigegeben;

    @OneToMany(mappedBy = "kinosaal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sitzreihe> sitzreihen = new ArrayList<>();

    /**
     * Markiert den Kinosaal als freigegeben.
     */
    public void freigeben() {
        this.freigegeben = true;
    }

    /**
     * Fügt eine Sitzreihe hinzu und pflegt die bidirektionale Beziehung.
     *
     * @param sitzreihe die hinzuzufügende Sitzreihe
     */
    public void addSitzreihe(Sitzreihe sitzreihe) {
        sitzreihen.add(sitzreihe);
        sitzreihe.setKinosaal(this);
    }
}
