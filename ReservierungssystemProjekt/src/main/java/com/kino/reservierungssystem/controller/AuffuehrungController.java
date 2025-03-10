package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.AuffuehrungDTO;
import com.kino.reservierungssystem.service.AuffuehrungService;
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
    public List<AuffuehrungDTO> getAllAuffuehrungen() {
        return auffuehrungService.getAllAuffuehrungen();
    }

    @GetMapping("/{id}")
    public AuffuehrungDTO getAuffuehrungById(@PathVariable Long id) {
        return auffuehrungService.getAuffuehrungById(id);
    }

    @PostMapping
    public AuffuehrungDTO createAuffuehrung(@RequestBody AuffuehrungDTO auffuehrungDTO) {
        return auffuehrungService.createAuffuehrung(auffuehrungDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAuffuehrung(@PathVariable Long id) {
        auffuehrungService.deleteAuffuehrung(id);
    }
}