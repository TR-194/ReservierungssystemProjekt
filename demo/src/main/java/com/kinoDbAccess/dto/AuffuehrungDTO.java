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
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuffuehrungDTO {
    public Long id;
    public LocalDate datum;
    public LocalTime uhrzeit;
    public Long filmId;
    public Long kinosaalId;

    public Preismodell preismodell;
}
