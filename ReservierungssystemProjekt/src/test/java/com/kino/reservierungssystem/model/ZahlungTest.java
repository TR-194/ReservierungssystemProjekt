package com.kino.reservierungssystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZahlungTest {

    @Test
    void testVerarbeiten() {
        Zahlung zahlung = new Zahlung();
        zahlung.setBetrag(50.0);
        // In der Simulation soll verarbeiten() immer true zurückgeben.
        assertTrue(zahlung.verarbeiten());
    }
}
