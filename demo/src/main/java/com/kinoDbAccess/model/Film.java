package com.kinoDbAccess.model;

public class Film {
    private Long id;
    private String name;
    private String alterbeschraenkung;
    private int dauer; //in Minuten

    public Film(Long id, String name, String alterbeschraenkung, int dauer) {
        this.id = id;
        this.name = name;
        this.alterbeschraenkung = alterbeschraenkung;
        this.dauer = dauer;
    }

    public Film(String name, String alterbeschraenkung, int dauer) {
        this.name = name;
        this.alterbeschraenkung = alterbeschraenkung;
        this.dauer = dauer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getAlterbeschraenkung() {
        return alterbeschraenkung;
    }

    public void setAlterbeschraenkung(String alterbeschraenkung) {
        this.alterbeschraenkung = alterbeschraenkung;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alterbeschraenkung='" + alterbeschraenkung + '\'' +
                ", dauer=" + dauer +
                '}';
    }
}
