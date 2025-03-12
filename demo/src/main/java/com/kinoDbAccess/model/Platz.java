package com.kinoDbAccess.model;

public class Platz {
    private Long id;
    private int nr;
    private Long reiheId;

    public Platz(Long id, int nr, Long reiheId) {
        this.id = id;
        this.nr = nr;
        this.reiheId = reiheId;
    }

    public Platz(int nr, Long reiheId) {
        this.nr = nr;
        this.reiheId = reiheId;
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

    public Long getReiheId() {
        return reiheId;
    }

    public void setReiheId(Long reiheId) {
        this.reiheId = reiheId;
    }

    @Override
    public String toString() {
        return "Platz{" +
                "id=" + id +
                ", nr=" + nr +
                ", reiheId=" + reiheId +
                '}';
    }
}
