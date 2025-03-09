package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.model.Auffuehrung;
import com.kino.reservierungssystem.service.AuffuehrungService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auffuehrungen")
public class AuffuehrungController {

    private final AuffuehrungService auffuehrungService;

    public AuffuehrungController(AuffuehrungService auffuehrungService) {
        this.auffuehrungService = auffuehrungService;
    }

    @GetMapping
    public ResponseEntity<List<Auffuehrung>> getAllAuffuehrungen() {
        List<Auffuehrung> auffuehrungen = auffuehrungService.getAllAuffuehrungen();
        return ResponseEntity.ok(auffuehrungen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auffuehrung> getAuffuehrungById(@PathVariable Long id) {
        Auffuehrung auffuehrung = auffuehrungService.getAuffuehrungById(id);
        return ResponseEntity.ok(auffuehrung);
    }

    @PostMapping
    public ResponseEntity<Auffuehrung> createAuffuehrung(@RequestBody Auffuehrung auffuehrung) {
        Auffuehrung savedAuffuehrung = auffuehrungService.saveAuffuehrung(auffuehrung);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuffuehrung);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auffuehrung> updateAuffuehrung(@PathVariable Long id, @RequestBody Auffuehrung auffuehrungDetails) {
        Auffuehrung updatedAuffuehrung = auffuehrungService.updateAuffuehrung(id, auffuehrungDetails);
        return ResponseEntity.ok(updatedAuffuehrung);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuffuehrung(@PathVariable Long id) {
        auffuehrungService.deleteAuffuehrung(id);
        return ResponseEntity.noContent().build();
    }
}