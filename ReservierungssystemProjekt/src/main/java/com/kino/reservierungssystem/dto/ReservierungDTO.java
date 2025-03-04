package com.kino.reservierungssystem.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReservierungDTO {
    private Long id;
    private Long kundeId;
    private Long auffuehrungId;
    private List<Long> sitzplatzIds; // Mehrere Sitzplätze
    private String status;
}
