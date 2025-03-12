package com.kinoDbAccess.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SitzreiheDTO {
    private Long id;
    private int reihenNummer;
    private List<SitzplatzDTO> sitzplaetze; // Sitzplätze haben ihre eigene Kategorie
}
