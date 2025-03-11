package com.kino.reservierungssystem.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "statistik")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistik {

    private String id;
    private double einnahmen;
}