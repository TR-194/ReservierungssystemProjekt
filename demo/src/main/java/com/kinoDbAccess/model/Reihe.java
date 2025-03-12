package com.kinoDbAccess.model;

public class Reihe {
    private Long id;
    private int nr;
    private Long saalId;

    public Reihe(Long id, int nr, Long saalId) {
        this.id = id;
        this.nr = nr;
        this.saalId = saalId;
    }

    public Reihe(int nr, Long saalId) {
        this.nr = nr;
        this.saalId = saalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public Long getSaalId() {
        return saalId;
    }

    public void setSaalId(Long saalId) {
        this.saalId = saalId;
    }

    @Override
    public String toString() {
        return "Reihe{" +
                "id=" + id +
                ", nr=" + nr +
                ", saalId=" + saalId +
                '}';
    }
}
