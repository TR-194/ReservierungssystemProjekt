package com.kinoDbAccess.model;

public class Preismodell {
    private long id;
    private double parkettPreis;
    private double logePreis;
    private double logeMitServicePreis;

    public Preismodell(long id, double parkettPreis, double logePreis, double logeMitServicePreis) {
        this.id = id;
        this.parkettPreis = parkettPreis;
        this.logePreis = logePreis;
        this.logeMitServicePreis = logeMitServicePreis;
    }

    public Preismodell(double parkettPreis, double logePreis, double logeMitServicePreis) {
        this.parkettPreis = parkettPreis;
        this.logePreis = logePreis;
        this.logeMitServicePreis = logeMitServicePreis;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getParkettPreis() {
        return parkettPreis;
    }

    public void setParkettPreis(double parkettPreis) {
        this.parkettPreis = parkettPreis;
    }

    public double getLogePreis() {
        return logePreis;
    }

    public void setLogePreis(double logePreis) {
        this.logePreis = logePreis;
    }

    public double getLogeMitServicePreis() {
        return logeMitServicePreis;
    }

    public void setLogeMitServicePreis(double logeMitServicePreis) {
        this.logeMitServicePreis = logeMitServicePreis;
    }

    @Override
    public String toString() {
        return "Preismodell{" +
                "id=" + id +
                ", parkettPreis=" + parkettPreis +
                ", logePreis=" + logePreis +
                ", logeMitServicePreis=" + logeMitServicePreis +
                '}';
    }
}
