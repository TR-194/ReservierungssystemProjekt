package com.kino.reservierungssystem.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ZahlungDTO {
    private Long id;
    private Long buchungId;
    private Double betrag;
    private Date zahlungsdatum;
}
