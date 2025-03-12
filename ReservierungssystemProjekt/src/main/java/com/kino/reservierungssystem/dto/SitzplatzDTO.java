package com.kino.reservierungssystem.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SitzplatzDTO implements Serializable {
    private Long id;
    private int nummer;
    private String status; // "frei", "reserviert", "gebucht"
    private Long SitzreiheId;
}
