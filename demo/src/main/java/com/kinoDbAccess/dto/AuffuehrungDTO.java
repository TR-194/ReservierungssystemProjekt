package com.kinoDbAccess.dto;

import com.kinoDbAccess.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuffuehrungDTO {
    public Long id;
    public LocalDate datum;
    public LocalTime uhrzeit;
    public Long filmId;
    public Long kinosaalId;

    public Preismodell preismodell;

    public AuffuehrungDTO(Long id, LocalDate datum, LocalTime uhrzeit, Long filmId, Long kinosaalId, Preismodell preismodell) {
        this.id = id;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.filmId = filmId;
        this.kinosaalId = kinosaalId;
        this.preismodell = preismodell;
    }
}
