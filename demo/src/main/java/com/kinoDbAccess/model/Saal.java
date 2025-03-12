package com.kinoDbAccess.model;

import org.springframework.stereotype.Component;

import java.util.List;

public class Saal {

    public Saal(boolean freigegeben, String name) {
        this.freigegeben = freigegeben;
        this.name = name;
    }

    public Saal(Long id, boolean freigegeben, String name) {
        this.id = id;
        this.freigegeben = freigegeben;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFreigegeben() {
        return freigegeben;
    }

    public void setFreigegeben(boolean freigegeben) {
        this.freigegeben = freigegeben;
    }

    private boolean freigegeben;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public Saal(String Name){
        name = Name;
    }

    @Override
    public String toString() {
        return "Saal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
