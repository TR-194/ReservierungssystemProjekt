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
public class KinosaalDTO {
    private Long id;
    private String name;
    private boolean freigegeben;
    private List<SitzreiheDTO> sitzreihen;
}
