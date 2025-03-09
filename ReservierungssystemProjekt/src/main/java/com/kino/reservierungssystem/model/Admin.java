package com.kino.reservierungssystem.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Repräsentiert einen Admin, der Filme, Aufführungen und Kinosäle verwaltet.
 * Die Einnahmenberechnung erfolgt nun über einen separaten MongoDB-gestützten Statistik-Service.
 */
public class Admin {

    public Film erstelleFilm(String titel, String genre, int dauer) {
        Film film = new Film();
        film.setTitel(titel);
        film.setAlterbeschraenkung(genre);
        film.setDauer(dauer);
        return film;
    }

    public void bearbeiteFilm(Film film, String neuerTitel, String neuesGenre, int neueDauer) {
        film.setTitel(neuerTitel);
        film.setAlterbeschraenkung(neuesGenre);
        film.setDauer(neueDauer);
    }

    public void loescheFilm(Film film) {
        System.out.println("Film '" + film.getTitel() + "' wurde gelöscht (theoretisch).");
        // In einer echten Anwendung wird hier der Film aus der Datenbank entfernt.
    }

    public Auffuehrung erstelleAuffuehrung(Film film, Kinosaal saal, LocalDate datum, LocalTime uhrzeit) {
        Auffuehrung auffuehrung = new Auffuehrung();
        auffuehrung.setFilm(film);
        auffuehrung.setKinosaal(saal);
        auffuehrung.setDatum(datum);
        auffuehrung.setUhrzeit(uhrzeit);
        return auffuehrung;
    }

    public void bearbeiteAuffuehrung(Auffuehrung auffuehrung, LocalDate neuesDatum, LocalTime neueUhrzeit, Kinosaal neuerSaal) {
        auffuehrung.setDatum(neuesDatum);
        auffuehrung.setUhrzeit(neueUhrzeit);
        auffuehrung.setKinosaal(neuerSaal);
    }

    public void loescheAuffuehrung(Auffuehrung auffuehrung) {
        System.out.println("Aufführung am " + auffuehrung.getDatum() + " wurde gelöscht (theoretisch).");
        // In einer echten Anwendung wird hier die Aufführung aus der Datenbank entfernt.
    }

    public Kinosaal erstelleKinosaal(String name) {
        Kinosaal saal = new Kinosaal();
        saal.setName(name);
        saal.setFreigegeben(false);
        return saal;
    }

    public void bestuhlungHinzufuegen(Kinosaal saal, List<Sitzreihe> reihen) {
        if (saal.getSitzreihen() == null) {
            saal.setSitzreihen(reihen);
        } else {
            saal.getSitzreihen().addAll(reihen);
        }
    }

    /**
     * Diese Methoden zur Einnahmenanzeige wurden entfernt, da die Berechnung über MongoDB erfolgt.
     * Alternativ können sie an einen dedizierten MongoDB-basierten Statistik-Service delegiert werden.
     */
    public double zeigeEinnahmenProAuffuehrung(Auffuehrung auffuehrung) {
        throw new UnsupportedOperationException("Berechnung der Einnahmen erfolgt über MongoDB.");
    }

    public double zeigeEinnahmenProFilm(Film film) {
        throw new UnsupportedOperationException("Berechnung der Einnahmen erfolgt über MongoDB.");
    }
}
