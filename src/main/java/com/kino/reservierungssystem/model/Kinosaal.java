package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kinosaal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean freigegeben;

    @OneToMany(mappedBy = "kinosaal")
    private List<Sitzreihe> sitzreihen;

    // Getter & Setter
}
