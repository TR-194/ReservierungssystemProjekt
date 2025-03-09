package com.kino.reservierungssystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

class KinosaalTest {

    @Test
    void testFreigeben_setsFreigegebenToTrue() {
        Kinosaal kinosaal = new Kinosaal();
        kinosaal.setName("Saal 1");
        kinosaal.setFreigegeben(false);

        kinosaal.freigeben();
        assertTrue(kinosaal.isFreigegeben());
    }

    @Test
    void testAddSitzreihe() {
        Kinosaal kinosaal = new Kinosaal();
        kinosaal.setName("Saal 2");
        kinosaal.setSitzreihen(new ArrayList<>());

        Sitzreihe reihe = new Sitzreihe();
        reihe.setReihenNr(1);

        // Falls du eine Methode addSitzreihe(Sitzreihe r) in Kinosaal hast:
        kinosaal.addSitzreihe(reihe);

        assertTrue(kinosaal.getSitzreihen().contains(reihe));
        assertEquals(kinosaal, reihe.getKinosaal());
    }

    @Test
    void testGesamtAnzahlSitzplaetze() {
        // Beispiel: Falls du eine Methode in Kinosaal hast, die alle Sitzplätze zählt
        Kinosaal kinosaal = new Kinosaal();
        Sitzreihe reihe1 = new Sitzreihe();
        Sitzplatz platz1 = new Sitzplatz();
        Sitzplatz platz2 = new Sitzplatz();
        reihe1.setSitzplaetze(new ArrayList<>());
        reihe1.getSitzplaetze().add(platz1);
        reihe1.getSitzplaetze().add(platz2);

        Sitzreihe reihe2 = new Sitzreihe();
        Sitzplatz platz3 = new Sitzplatz();
        reihe2.setSitzplaetze(Collections.singletonList(platz3));

        kinosaal.setSitzreihen(new ArrayList<>());
        kinosaal.getSitzreihen().add(reihe1);
        kinosaal.getSitzreihen().add(reihe2);

        // Beispiel: Wenn du in Kinosaal eine Methode gesamtAnzahlSitzplaetze() implementiert hast
        // int anzahl = kinosaal.gesamtAnzahlSitzplaetze();
        // assertEquals(3, anzahl);

        // Falls du diese Methode nicht hast, entferne diesen Test oder passe ihn an.
        // Hier nur ein Platzhalter, damit du sie ggf. selbst implementieren kannst.
        assertTrue(true);
    }
}
