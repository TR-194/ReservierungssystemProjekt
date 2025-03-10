package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.SitzplatzDTO;
import com.kino.reservierungssystem.service.SitzplatzService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sitzplaetze")
public class SitzplatzController {

    private final SitzplatzService sitzplatzService;

    public SitzplatzController(SitzplatzService sitzplatzService) {
        this.sitzplatzService = sitzplatzService;
    }

    @GetMapping
    public List<SitzplatzDTO> getAllSitzplaetze() {
        return sitzplatzService.getAllSitzplaetze();
    }

    @PutMapping("/{id}/status")
    public SitzplatzDTO updateSitzplatzStatus(@PathVariable Long id, @RequestParam String status) {
        return sitzplatzService.updateSitzplatzStatus(id, status);
    }
}
