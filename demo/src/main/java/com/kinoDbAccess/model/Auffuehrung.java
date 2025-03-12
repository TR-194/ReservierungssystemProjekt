package com.kinoDbAccess.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;

public class Auffuehrung {
    private long id;
    private long filmId;
    private LocalDate datum;
    private LocalTime uhrzeit;
    private long saalId;
    private long preismodellId;

    public Auffuehrung(long id, long filmId, LocalDate datum, LocalTime uhrzeit, long saalId, long PreismodellId) {
        this.id = id;
        this.filmId = filmId;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.saalId = saalId;
        this.preismodellId = PreismodellId;
    }

    public Auffuehrung(long filmId, LocalDate datum, LocalTime uhrzeit, long saalId, long PreismodellId) {
        this.filmId = filmId;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.saalId = saalId;
        this.preismodellId = PreismodellId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(LocalTime uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    public long getSaalId() {
        return saalId;
    }

    public void setSaalId(long saalId) {
        this.saalId = saalId;
    }

    @Override
    public String toString() {
        return "Auffuehrung{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", datum=" + datum +
                ", uhrzeit=" + uhrzeit +
                ", saalId=" + saalId +
                ", prismodellId=" + preismodellId +
                '}';
    }

    public long getPreismodellId() {
        return preismodellId;
    }

    public void setPreismodellId(long preismodellId) {
        this.preismodellId = preismodellId;
    }
}
