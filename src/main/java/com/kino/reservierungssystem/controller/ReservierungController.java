package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.model.Reservierung;
import com.kino.reservierungssystem.service.ReservierungService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservierungen")
public class ReservierungController {
    private final ReservierungService reservierungService;

    public ReservierungController(ReservierungService reservierungService) {
        this.reservierungService = reservierungService;
    }

    @GetMapping("/{kundeId}")
    public List<Reservierung> getReservierungenByKunde(@PathVariable Long kundeId) {
        return reservierungService.getReservierungenByKunde(kundeId);
    }

    @PostMapping
    public Reservierung createReservierung(@RequestBody Reservierung reservierung) {
        return reservierungService.saveReservierung(reservierung);
    }
}
