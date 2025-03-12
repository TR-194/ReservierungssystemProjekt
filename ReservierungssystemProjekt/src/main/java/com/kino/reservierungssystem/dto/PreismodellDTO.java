package com.kino.reservierungssystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreismodellDTO {
    private Long id; // Falls es mehrere Preismodelle gibt
    private double logeMitServicePreis;
    private double logePreis;
    private double parkettPreis;
}
