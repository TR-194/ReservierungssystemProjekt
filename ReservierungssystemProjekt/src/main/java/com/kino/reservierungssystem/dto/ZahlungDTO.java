package com.kino.reservierungssystem.dto;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZahlungDTO {
    private Long id;
    private double betrag;
    private LocalDate zahlungsdatum;
    private Long buchungId;  // <-- Make sure this field exists
}
