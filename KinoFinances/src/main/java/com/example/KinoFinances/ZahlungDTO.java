package com.example.KinoFinances;

import java.time.LocalDate;

public class ZahlungDTO {
    private Long id;
    private double betrag;
    private LocalDate zahlungsdatum;
    private Long buchungId;  // <-- Make sure this field exists

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public LocalDate getZahlungsdatum() {
        return zahlungsdatum;
    }

    public void setZahlungsdatum(LocalDate zahlungsdatum) {
        this.zahlungsdatum = zahlungsdatum;
    }

    public Long getBuchungId() {
        return buchungId;
    }

    public void setBuchungId(Long buchungId) {
        this.buchungId = buchungId;
    }
}
