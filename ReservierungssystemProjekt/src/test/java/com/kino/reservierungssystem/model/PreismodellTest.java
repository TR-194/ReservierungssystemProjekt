package com.kino.reservierungssystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PreismodellTest {

    @Test
    void testBerechnePreis() {
        Preismodell preismodell = new Preismodell();
        preismodell.setParkettPreis(10.0);
        preismodell.setLogePreis(15.0);
        preismodell.setLogeMitServicePreis(20.0);

        assertEquals(10.0, preismodell.berechnePreis(Kategorie.PARKETT));
        assertEquals(15.0, preismodell.berechnePreis(Kategorie.LOGE));
        assertEquals(20.0, preismodell.berechnePreis(Kategorie.LOGE_MIT_SERVICE));
    }
}
