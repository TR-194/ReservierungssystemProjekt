package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.exception.ZahlungFehlgeschlagenException;
import com.kino.reservierungssystem.model.Zahlung;
import com.kino.reservierungssystem.service.ZahlungService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zahlungen")
public class ZahlungController {

    private final ZahlungService zahlungService;

    public ZahlungController(ZahlungService zahlungService) {
        this.zahlungService = zahlungService;
    }

    @PostMapping
    public ResponseEntity<Zahlung> createZahlung(@RequestBody Zahlung zahlung) {
        try {
            Zahlung savedZahlung = zahlungService.saveZahlung(zahlung);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedZahlung);
        } catch (ZahlungFehlgeschlagenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
