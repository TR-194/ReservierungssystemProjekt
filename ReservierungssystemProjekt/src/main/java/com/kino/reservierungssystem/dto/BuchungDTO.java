package com.kino.reservierungssystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuchungDTO {
    private Long id;
    private Long auffuehrungId;
    private List<SitzplatzDTO> sitzplaetze;
    private ZahlungDTO zahlung;
    private String email;
}
