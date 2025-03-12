package com.example.KinoFinances;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Income")
public class Payment {
    public Payment(long auffuehrungId, String film, double betrag, LocalDate datum) {
        AuffuehrungId = auffuehrungId;
        Film = film;
        Betrag = betrag;
        Datum = datum;
    }

    public Payment() {
    }

    public long getAuffuehrungId() {
        return AuffuehrungId;
    }

    public void setAuffuehrungId(long auffuehrungId) {
        AuffuehrungId = auffuehrungId;
    }

    private long AuffuehrungId;

    public String getFilm() {
        return Film;
    }

    public void setFilm(String film) {
        Film = film;
    }

    public double getBetrag() {
        return Betrag;
    }

    public void setBetrag(double betrag) {
        Betrag = betrag;
    }

    public LocalDate getDatum() {
        return Datum;
    }

    public void setDatum(LocalDate datum) {
        Datum = datum;
    }

    private String Film;
    private double Betrag;
    private LocalDate Datum;

    @Override
    public String toString() {
        return "Payment{" +
                "AuffuehrungId=" + AuffuehrungId +
                ", Film='" + Film + '\'' +
                ", Betrag=" + Betrag +
                ", Datum=" + Datum +
                '}';
    }
}
