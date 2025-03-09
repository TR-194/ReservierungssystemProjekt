package com.kino.reservierungssystem.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void testErstelleFilm() {
        Admin admin = new Admin();
        Film film = admin.erstelleFilm("Avatar", "PG-13", 160);
        assertNotNull(film);
        assertEquals("Avatar", film.getTitel());
        assertEquals("PG-13", film.getAlterbeschraenkung());
        assertEquals(160, film.getDauer());
    }

    @Test
    void testBearbeiteFilm() {
        Admin admin = new Admin();
        Film film = new Film();
        film.setTitel("Old Title");
        film.setAlterbeschraenkung("PG");
        film.setDauer(100);
        admin.bearbeiteFilm(film, "New Title", "R", 120);
        assertEquals("New Title", film.getTitel());
        assertEquals("R", film.getAlterbeschraenkung());
        assertEquals(120, film.getDauer());
    }

    @Test
    void testLoescheFilm() {
        Admin admin = new Admin();
        Film film = new Film();
        film.setTitel("Test Film");
        // Es wird hier nur geprüft, dass keine Exception geworfen wird.
        assertDoesNotThrow(() -> admin.loescheFilm(film));
    }

    @Test
    void testErstelleAuffuehrung() {
        Admin admin = new Admin();
        Film film = new Film();
        Kinosaal saal = new Kinosaal();
        LocalDate datum = LocalDate.now();
        LocalTime zeit = LocalTime.of(20, 0);
        Auffuehrung auffuehrung = admin.erstelleAuffuehrung(film, saal, datum, zeit);
        assertNotNull(auffuehrung);
        assertEquals(film, auffuehrung.getFilm());
        assertEquals(saal, auffuehrung.getKinosaal());
        assertEquals(datum, auffuehrung.getDatum());
        assertEquals(zeit, auffuehrung.getUhrzeit());
    }

    @Test
    void testBearbeiteAuffuehrung() {
        Admin admin = new Admin();
        Auffuehrung auffuehrung = new Auffuehrung();
        LocalDate originalDatum = LocalDate.now();
        LocalTime originalZeit = LocalTime.of(18, 0);
        Kinosaal originalSaal = new Kinosaal();
        auffuehrung.setDatum(originalDatum);
        auffuehrung.setUhrzeit(originalZeit);
        auffuehrung.setKinosaal(originalSaal);

        LocalDate neuesDatum = originalDatum.plusDays(1);
        LocalTime neueZeit = LocalTime.of(20, 0);
        Kinosaal neuerSaal = new Kinosaal();
        admin.bearbeiteAuffuehrung(auffuehrung, neuesDatum, neueZeit, neuerSaal);
        assertEquals(neuesDatum, auffuehrung.getDatum());
        assertEquals(neueZeit, auffuehrung.getUhrzeit());
        assertEquals(neuerSaal, auffuehrung.getKinosaal());
    }

    @Test
    void testLoescheAuffuehrung() {
        Admin admin = new Admin();
        Auffuehrung auffuehrung = new Auffuehrung();
        auffuehrung.setDatum(LocalDate.now());
        // Es wird nur geprüft, dass kein Fehler auftritt.
        assertDoesNotThrow(() -> admin.loescheAuffuehrung(auffuehrung));
    }

    @Test
    void testErstelleKinosaal() {
        Admin admin = new Admin();
        Kinosaal saal = admin.erstelleKinosaal("Saal 1");
        assertNotNull(saal);
        assertEquals("Saal 1", saal.getName());
        assertFalse(saal.isFreigegeben());
    }

    @Test
    void testBestuhlungHinzufuegen() {
        Admin admin = new Admin();
        Kinosaal saal = new Kinosaal();
        // Falls der Sitzreihen-Liste anfangs null ist.
        saal.setSitzreihen(null);
        Sitzreihe reihe1 = new Sitzreihe();
        Sitzreihe reihe2 = new Sitzreihe();
        List<Sitzreihe> reihen = Arrays.asList(reihe1, reihe2);
        admin.bestuhlungHinzufuegen(saal, reihen);
        assertNotNull(saal.getSitzreihen());
        assertTrue(saal.getSitzreihen().contains(reihe1));
        assertTrue(saal.getSitzreihen().contains(reihe2));
    }

    @Test
    void testZeigeEinnahmenProAuffuehrungThrowsException() {
        Admin admin = new Admin();
        Auffuehrung auffuehrung = new Auffuehrung();
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> admin.zeigeEinnahmenProAuffuehrung(auffuehrung));
        assertEquals("Berechnung der Einnahmen erfolgt über MongoDB.", exception.getMessage());
    }

    @Test
    void testZeigeEinnahmenProFilmThrowsException() {
        Admin admin = new Admin();
        Film film = new Film();
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> admin.zeigeEinnahmenProFilm(film));
        assertEquals("Berechnung der Einnahmen erfolgt über MongoDB.", exception.getMessage());
    }
}
