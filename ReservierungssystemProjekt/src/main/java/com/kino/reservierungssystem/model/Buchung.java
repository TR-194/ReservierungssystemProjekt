package com.kino.reservierungssystem.model;


import com.kino.reservierungssystem.exception.PlatzNichtVerfuegbarException;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Buchung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kunde_id")
    private Kunde kunde;

    @ManyToOne
    @JoinColumn(name = "auffuehrung_id")
    private Auffuehrung auffuehrung;

    @OneToMany(mappedBy = "buchung")
    private List<Sitzplatz> sitzplaetze;

    @OneToOne(mappedBy = "buchung")
    private Zahlung zahlung;

    /**
     * Führt die Online-Zahlung aus und sichert über Locks, dass alle zugehörigen Sitzplätze atomar
     * in den Status GEBUCHT übergehen. Falls ein Sitzplatz nicht verfügbar ist, wird eine
     * PlatzNichtVerfuegbarException geworfen.
     */
    public void bezahlen() {
        if (zahlung != null && zahlung.verarbeiten()) {
            // Für jeden Sitzplatz versuchen, den Lock zu erwerben
            for (Sitzplatz platz : sitzplaetze) {
                boolean acquired = platz.getLock().tryLock();
                if (!acquired) {
                    throw new PlatzNichtVerfuegbarException("Ticket bereits von einem anderen Prozess gebucht: Platz " + platz.getPlatzNr());
                }
            }
            try {
                // Überprüfen, ob alle gesperrten Sitzplätze noch den Status RESERVIERT haben
                for (Sitzplatz platz : sitzplaetze) {
                    if (platz.getStatus() != Status.RESERVIERT) {
                        throw new PlatzNichtVerfuegbarException("Ticket nicht mehr verfügbar: Platz " + platz.getPlatzNr());
                    }
                }
                // Alle gesperrten Sitzplätze auf GEBUCHT setzen
                for (Sitzplatz platz : sitzplaetze) {
                    platz.setStatus(Status.GEBUCHT);
                }
            } finally {
                // Alle erworbenen Locks freigeben
                for (Sitzplatz platz : sitzplaetze) {
                    if (platz.getLock().isHeldByCurrentThread()) {
                        platz.getLock().unlock();
                    }
                }
            }
        } else {
            throw new RuntimeException("Online-Zahlung fehlgeschlagen.");
        }
    }
}
