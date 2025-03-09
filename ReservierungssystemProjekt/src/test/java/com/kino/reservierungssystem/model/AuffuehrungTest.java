package com.kino.reservierungssystem.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class AuffuehrungTest {

    @Test
    void testIstVerfuegbar_returnsTrue_whenAtLeastOneSeatIsFree() {
        // Erstelle einen freien Sitzplatz
        Sitzplatz freiSeat = new Sitzplatz();
        freiSeat.setPlatzNr(1);
        freiSeat.setStatus(Status.FREI);

        // Erstelle einen reservierten Sitzplatz als Beispiel
        Sitzplatz reservedSeat = new Sitzplatz();
        reservedSeat.setPlatzNr(2);
        reservedSeat.setStatus(Status.RESERVIERT);

        // Erstelle eine Sitzreihe mit beiden Sitzplätzen
        Sitzreihe sitzreihe = new Sitzreihe();
        sitzreihe.setSitzplaetze(Arrays.asList(freiSeat, reservedSeat));

        // Erstelle einen Kinosaal mit der Sitzreihe
        Kinosaal kinosaal = new Kinosaal();
        kinosaal.setSitzreihen(Collections.singletonList(sitzreihe));

        // Erstelle eine Aufführung und weise den Kinosaal zu
        Auffuehrung auffuehrung = new Auffuehrung();
        auffuehrung.setKinosaal(kinosaal);

        // Erwartet: Mindestens ein Sitzplatz (freiSeat) ist frei → istVerfuegbar() liefert true
        assertTrue(auffuehrung.istVerfuegbar());
    }

    @Test
    void testIstVerfuegbar_returnsFalse_whenNoSeatIsFree() {
        // Erstelle zwei Sitzplätze, die nicht frei sind
        Sitzplatz reservedSeat = new Sitzplatz();
        reservedSeat.setPlatzNr(1);
        reservedSeat.setStatus(Status.RESERVIERT);

        Sitzplatz bookedSeat = new Sitzplatz();
        bookedSeat.setPlatzNr(2);
        bookedSeat.setStatus(Status.GEBUCHT);

        // Erstelle eine Sitzreihe mit den beiden Sitzplätzen
        Sitzreihe sitzreihe = new Sitzreihe();
        sitzreihe.setSitzplaetze(Arrays.asList(reservedSeat, bookedSeat));

        // Erstelle einen Kinosaal mit der Sitzreihe
        Kinosaal kinosaal = new Kinosaal();
        kinosaal.setSitzreihen(Collections.singletonList(sitzreihe));

        // Erstelle eine Aufführung und weise den Kinosaal zu
        Auffuehrung auffuehrung = new Auffuehrung();
        auffuehrung.setKinosaal(kinosaal);

        // Erwartet: Keine Sitzplätze sind frei → istVerfuegbar() liefert false
        assertFalse(auffuehrung.istVerfuegbar());
    }

    @Test
    void testIstVerfuegbar_returnsFalse_whenKinosaalIsNull() {
        // Wenn kein Kinosaal zugeordnet ist, kann keine Verfügbarkeit überprüft werden
        Auffuehrung auffuehrung = new Auffuehrung();
        auffuehrung.setKinosaal(null);

        // Erwartet: istVerfuegbar() liefert false
        assertFalse(auffuehrung.istVerfuegbar());
    }
}
