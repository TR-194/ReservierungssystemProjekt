package com.kinoDbAccess.dto;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZahlungDTO {
    private Long id;
    private double betrag;
    private String filmtitel;
    private Long auffuehrungId;
    private LocalDate zahlungsdatum;
    private Long buchungId;
}