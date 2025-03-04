package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.model.Zahlung;
import com.kino.reservierungssystem.service.ZahlungService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zahlungen")
public class ZahlungController {
    private final ZahlungService zahlungService;

    public ZahlungController(ZahlungService zahlungService) {
        this.zahlungService = zahlungService;
    }

    @PostMapping
    public Zahlung createZahlung(@RequestBody Zahlung zahlung) {
        return zahlungService.saveZahlung(zahlung);
    }
}
