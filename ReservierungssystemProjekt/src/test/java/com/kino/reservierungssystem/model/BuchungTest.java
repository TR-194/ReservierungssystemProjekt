package com.kino.reservierungssystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class BuchungTest {

    @Test
    void testBezahlenSuccessful() {
        Buchung buchung = new Buchung();

        // Erstelle zwei Sitzplätze mit Status RESERVIERT.
        Sitzplatz platz1 = new Sitzplatz();
        platz1.setPlatzNr(1);
        platz1.setStatus(Status.RESERVIERT);
        Sitzplatz platz2 = new Sitzplatz();
        platz2.setPlatzNr(2);
        platz2.setStatus(Status.RESERVIERT);
        buchung.setSitzplaetze(Arrays.asList(platz1, platz2));

        // Erstelle eine Dummy-Zahlung, die immer true zurückgibt.
        Zahlung zahlung = new Zahlung();
        zahlung.setBetrag(20.0);
        buchung.setZahlung(zahlung);

        // Methode bezahlen() ausführen.
        buchung.bezahlen();

        // Überprüfe, ob beide Sitzplätze den Status GEBUCHT haben.
        assertEquals(Status.GEBUCHT, platz1.getStatus());
        assertEquals(Status.GEBUCHT, platz2.getStatus());
    }
}
