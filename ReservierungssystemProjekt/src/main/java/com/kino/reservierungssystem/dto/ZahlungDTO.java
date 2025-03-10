package com.kino.reservierungssystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZahlungDTO {
    private String transaktionsId; // Eindeutige ID für die Zahlung
    private double betrag;
    private LocalDate zahlungsdatum;
    private String methode; // z. B. "Bar",
}
