package com.kinoDbAccess.model;

import org.springframework.stereotype.Component;

import java.util.List;

public class Saal {

    public Long getId() {
        return id;
    }

    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public Saal(Long Id, String Name){
        id = Id; name = Name;
    }

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
