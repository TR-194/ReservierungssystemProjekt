package com.kino.reservierungssystem.model;

import com.kino.reservierungssystem.exception.ReservierungAbgelaufenException;
import com.kino.reservierungssystem.exception.UngueltigeBuchungException;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ReservierungTest {

    @Test
    void testIstGueltig() {
        Reservierung reservierungFuture = new Reservierung();
        reservierungFuture.setAblaufDatum(LocalDate.now().plusDays(1));
        assertTrue(reservierungFuture.istGueltig());

        Reservierung reservierungPast = new Reservierung();
        reservierungPast.setAblaufDatum(LocalDate.now().minusDays(1));
        assertFalse(reservierungPast.istGueltig());
    }

    @Test
    void testStornieren() {
        Reservierung reservierung = new Reservierung();
        Sitzplatz platz1 = new Sitzplatz();
        platz1.setStatus(Status.RESERVIERT);
        Sitzplatz platz2 = new Sitzplatz();
        platz2.setStatus(Status.RESERVIERT);
        reservierung.setSitzplaetze(Arrays.asList(platz1, platz2));
        // Setze die Referenz der Reservierung in den Sitzplätzen
        platz1.setReservierung(reservierung);
        platz2.setReservierung(reservierung);

        reservierung.stornieren();

        assertEquals(Status.FREI, platz1.getStatus());
        assertEquals(Status.FREI, platz2.getStatus());
        assertNull(platz1.getReservierung());
        assertNull(platz2.getReservierung());
    }

    @Test
    void testInBuchungUmwandelnValid() {
        Reservierung reservierung = new Reservierung();
        reservierung.setAblaufDatum(LocalDate.now().plusDays(1));
        Sitzplatz platz1 = new Sitzplatz();
        platz1.setStatus(Status.RESERVIERT);
        Sitzplatz platz2 = new Sitzplatz();
        platz2.setStatus(Status.RESERVIERT);
        reservierung.setSitzplaetze(Arrays.asList(platz1, platz2));

        // Umwandlung sollte eine Buchung zurückliefern, ohne Exception zu werfen.
        Buchung buchung = reservierung.inBuchungUmwandeln();
        assertNotNull(buchung);
        // Überprüfe, ob die Buchung in den Sitzplätzen gesetzt wurde
        for (Sitzplatz p : reservierung.getSitzplaetze()) {
            assertEquals(buchung, p.getBuchung());
        }
    }

    @Test
    void testInBuchungUmwandelnExpired() {
        Reservierung reservierung = new Reservierung();
        reservierung.setAblaufDatum(LocalDate.now().minusDays(1));

        Exception exception = assertThrows(ReservierungAbgelaufenException.class, () -> {
            reservierung.inBuchungUmwandeln();
        });
        assertTrue(exception.getMessage().contains("Reservierung ist abgelaufen."));
    }
}
