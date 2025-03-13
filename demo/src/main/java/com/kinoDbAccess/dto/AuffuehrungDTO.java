package com.kinoDbAccess.dto;

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
    private Long id;
    private LocalDate datum;
    private LocalTime uhrzeit;
    private Long filmId;
    private Long kinosaalId;
    private PreismodellDTO preismodell;
}

