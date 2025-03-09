package com.kino.reservierungssystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class SitzreiheTest {

    @Test
    void testAddSitzplatz_setsBidirectionalRelationship() {
        Sitzreihe sitzreihe = new Sitzreihe();
        sitzreihe.setReihenNr(1);
        sitzreihe.setSitzplaetze(new ArrayList<>());

        Sitzplatz platz = new Sitzplatz();
        platz.setPlatzNr(5);

        // Falls du eine Methode addSitzplatz(Sitzplatz p) in Sitzreihe hast:
        sitzreihe.addSitzplatz(platz);

        assertTrue(sitzreihe.getSitzplaetze().contains(platz));
        assertEquals(sitzreihe, platz.getSitzreihe());
    }

    @Test
    void testKategorieAssignment() {
        Sitzreihe sitzreihe = new Sitzreihe();
        sitzreihe.setKategorie(Kategorie.LOGE);

        assertEquals(Kategorie.LOGE, sitzreihe.getKategorie());
    }
}
