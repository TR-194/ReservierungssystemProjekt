package com.kino.reservierungssystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    @Test
    void testFilmCreationAndProperties() {
        Film film = new Film();
        film.setTitel("Inception");
        film.setAlterbeschraenkung("PG-13");
        film.setDauer(148);

        assertEquals("Inception", film.getTitel());
        assertEquals("PG-13", film.getAlterbeschraenkung());
        assertEquals(148, film.getDauer());
    }

    @Test
    void testAddAndRemoveAuffuehrung() {
        Film film = new Film();
        film.setTitel("Inception");
        Auffuehrung auffuehrung = new Auffuehrung();
        film.addAuffuehrung(auffuehrung);

        // Nach dem Hinzufügen: Aufführungs-Liste enthält die Aufführung und das Film-Feld in der Aufführung ist gesetzt.
        assertTrue(film.getAuffuehrungen().contains(auffuehrung));
        assertEquals(film, auffuehrung.getFilm());

        // Nach dem Entfernen: Aufführungs-Liste ist leer und das Film-Feld in der Aufführung ist null.
        film.removeAuffuehrung(auffuehrung);
        assertFalse(film.getAuffuehrungen().contains(auffuehrung));
        assertNull(auffuehrung.getFilm());
    }
}
