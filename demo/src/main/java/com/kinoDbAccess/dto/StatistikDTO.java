package com.kinoDbAccess.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatistikDTO {
    @JsonProperty("einnahmen")
    private double einnahmen;

    public StatistikDTO(double einnahmen) {
        this.einnahmen = einnahmen;
    }

    public double getEinnahmen() {
        return einnahmen;
    }

    public void setEinnahmen(double einnahmen) {
        this.einnahmen = einnahmen;
    }
}
