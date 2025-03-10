package com.kino.reservierungssystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SitzplatzDTO {
    private Long id;
    private int nummer;
    private String status; // "frei", "reserviert", "gebucht"
    private Long auffuehrungId; // Zugehörige Aufführung
    private SitzkategorieDTO kategorie; // Sitzplatz hat eine Kategorie
}
