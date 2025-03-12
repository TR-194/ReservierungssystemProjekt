package com.kino.reservierungssystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auffuehrung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datum;
    private LocalTime uhrzeit;
    private Long filmId;  // Kafka sendet nur die Film-ID
    private Long kinosaalId; // Kafka sendet nur die Saal-ID

    @Version // Optimistisches Locking
    private int version;


}
