package com.kinoDbAccess.model;

public class Platz {
    private int id;
    private int nr;
    private int reiheId;

    public Platz(int id, int nr, int reiheId) {
        this.id = id;
        this.nr = nr;
        this.reiheId = reiheId;
    }

    public Platz(int nr, int reiheId) {
        this.nr = nr;
        this.reiheId = reiheId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public int getReiheId() {
        return reiheId;
    }

    public void setReiheId(int reiheId) {
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
