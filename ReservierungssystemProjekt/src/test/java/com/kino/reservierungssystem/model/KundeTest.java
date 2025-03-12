package com.kino.reservierungssystem.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class KundeTest {

    @Test
    void testKundeProperties() {
        Kunde kunde = new Kunde();
        kunde.setId(1L);
        kunde.setName("Max Mustermann");
        kunde.setEmail("max@example.com");

        assertEquals(1L, kunde.getId());
        assertEquals("Max Mustermann", kunde.getName());
        assertEquals("max@example.com", kunde.getEmail());
    }

    @Test
    void testAddReservierung() {
        // Falls du eine Methode addReservierung() implementiert hast:
        Kunde kunde = new Kunde();
        kunde.setReservierungen(new ArrayList<>());
        Reservierung reservierung = new Reservierung();

        // Beispielimplementierung: Kunde.addReservierung(reservierung)
        kunde.addReservierung(reservierung);

        // Überprüfe, ob die Reservierung in der Liste des Kunden ist
        assertTrue(kunde.getReservierungen().contains(reservierung));
        // Und dass in der Reservierung der Kunde korrekt gesetzt wurde
        assertEquals(kunde, reservierung.getKunde());
    }

    @Test
    void testAddBuchung() {
        // Falls du eine Methode addBuchung() implementiert hast:
        Kunde kunde = new Kunde();
        kunde.setBuchungen(new ArrayList<>());
        Buchung buchung = new Buchung();

        // Beispielimplementierung: Kunde.addBuchung(buchung)
        kunde.addBuchung(buchung);

        // Überprüfe, ob die Buchung in der Liste des Kunden enthalten ist
        assertTrue(kunde.getBuchungen().contains(buchung));
        // Und dass in der Buchung der Kunde korrekt gesetzt wurde
        assertEquals(kunde, buchung.getKunde());
    }
}
