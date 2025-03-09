package com.kino.reservierungssystem.model;

import com.kino.reservierungssystem.exception.ReservierungAbgelaufenException;
import com.kino.reservierungssystem.exception.UngueltigeBuchungException;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservierung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate ablaufDatum;

    @ManyToOne
    @JoinColumn(name = "kunde_id")
    private Kunde kunde;

    @ManyToOne
    @JoinColumn(name = "auffuehrung_id")
    private Auffuehrung auffuehrung;

    @OneToMany(mappedBy = "reservierung")
    private List<Sitzplatz> sitzplaetze;

    /**
     * Prüft, ob die Reservierung noch gültig ist.
     *
     * @return true, wenn das aktuelle Datum vor dem Ablaufdatum liegt.
     */
    public boolean istGueltig() {
        return LocalDate.now().isBefore(ablaufDatum);
    }

    /**
     * Wandelt die Reservierung in eine Buchung um, sofern sie noch gültig ist und alle Sitzplätze
     * den Status RESERVIERT besitzen.
     *
     * @return die neue Buchung
     * @throws ReservierungAbgelaufenException, falls die Reservierung abgelaufen ist
     * @throws UngueltigeBuchungException, falls ein Sitzplatz nicht mehr reserviert ist
     */
    public Buchung inBuchungUmwandeln() {
        if (!istGueltig()) {
            throw new ReservierungAbgelaufenException("Reservierung ist abgelaufen.");
        }
        if (sitzplaetze != null) {
            for (Sitzplatz platz : sitzplaetze) {
                if (platz.getStatus() != Status.RESERVIERT) {
                    throw new UngueltigeBuchungException("Mindestens ein Sitzplatz ist nicht mehr reserviert.");
                }
            }
        }
        Buchung buchung = new Buchung();
        buchung.setKunde(kunde);
        buchung.setAuffuehrung(auffuehrung);
        buchung.setSitzplaetze(sitzplaetze);
        if (sitzplaetze != null) {
            for (Sitzplatz platz : sitzplaetze) {
                platz.setBuchung(buchung);
            }
        }
        return buchung;
    }

    /**
     * Storniert die Reservierung, indem alle zugehörigen Sitzplätze freigegeben werden.
     */
    public void stornieren() {
        if (sitzplaetze != null) {
            for (Sitzplatz platz : sitzplaetze) {
                platz.setStatus(Status.FREI);
                platz.setReservierung(null);
            }
        }
    }
}
