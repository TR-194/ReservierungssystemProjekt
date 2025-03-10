package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.BuchungDTO;
import com.kino.reservierungssystem.service.BuchungService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buchungen")
public class BuchungController {

    private final BuchungService buchungService;

    public BuchungController(BuchungService buchungService) {
        this.buchungService = buchungService;
    }

    @GetMapping
    public List<BuchungDTO> getAllBuchungen() {
        return buchungService.getAllBuchungen();
    }

    @PostMapping
    public BuchungDTO createBuchung(@RequestBody BuchungDTO buchungDTO) {
        return buchungService.createBuchung(buchungDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBuchung(@PathVariable Long id) {
        buchungService.deleteBuchung(id);
    }
}
