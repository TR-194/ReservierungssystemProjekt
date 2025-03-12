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
public class ReservierungDTO {
    private Long id;
    private Long auffuehrungId;
    private List<Long> sitzplatzIds;
    private String status;
    private double preis;
    private String email;
    private String name;
}
