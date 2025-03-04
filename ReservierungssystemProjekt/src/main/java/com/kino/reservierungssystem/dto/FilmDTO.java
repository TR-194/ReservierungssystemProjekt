package com.kino.reservierungssystem.dto;

import lombok.Data;

@Data
public class FilmDTO {
    private Long id;
    private String titel;
    private String alterbeschraenkung;
    private int dauer;
}
