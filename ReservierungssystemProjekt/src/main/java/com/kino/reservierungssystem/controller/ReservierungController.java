package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.ReservierungDTO;
import com.kino.reservierungssystem.exception.ReservierungAbgelaufenException;
import com.kino.reservierungssystem.exception.UngueltigeBuchungException;
import com.kino.reservierungssystem.model.Buchung;
import com.kino.reservierungssystem.model.Reservierung;
import com.kino.reservierungssystem.service.ReservierungService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservierungen")
public class ReservierungController {

    private final ReservierungService reservierungService;

    public ReservierungController(ReservierungService reservierungService) {
        this.reservierungService = reservierungService;
    }

    @GetMapping
    public List<ReservierungDTO> getAllReservierungen() {
        return reservierungService.getAllReservierungen();
    }

    @GetMapping("/{kundeId}")
    public ResponseEntity<List<Reservierung>> getReservierungenByKunde(@PathVariable Long kundeId) {
        List<Reservierung> reservierungen = reservierungService.getReservierungenByKunde(kundeId);
        return ResponseEntity.ok(reservierungen);
    }

    @PostMapping
    public ResponseEntity<Reservierung> createReservierung(@RequestBody Reservierung reservierung) {
        Reservierung savedReservierung = reservierungService.saveReservierung(reservierung);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservierung);
    }

    /**
     * Endpoint, um eine bestehende Reservierung in eine Buchung umzuwandeln.
     */
    @PutMapping("/{reservierungsId}/buchen")
    public ResponseEntity<Buchung> convertReservierungToBuchung(@PathVariable Long reservierungsId) {
        try {
            Buchung buchung = reservierungService.convertToBuchung(reservierungsId);
            return ResponseEntity.ok(buchung);
        } catch (ReservierungAbgelaufenException | UngueltigeBuchungException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    /**
     * Endpoint, um eine Reservierung zu stornieren.
     */
    @DeleteMapping("/{reservierungsId}")
    public ResponseEntity<Void> cancelReservierung(@PathVariable Long reservierungsId) {
        reservierungService.cancelReservierung(reservierungsId);
        return ResponseEntity.noContent().build();
    }
}
