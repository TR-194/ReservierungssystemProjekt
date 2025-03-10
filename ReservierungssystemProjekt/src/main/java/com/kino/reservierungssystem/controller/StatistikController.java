package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.StatistikDTO;
import com.kino.reservierungssystem.service.StatistikService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistiken")
public class StatistikController {

    private final StatistikService statistikService;

    public StatistikController(StatistikService statistikService) {
        this.statistikService = statistikService;
    }

    @GetMapping("/einnahmen/auffuehrung/{auffuehrungId}")
    public StatistikDTO getEinnahmenProAuffuehrung(@PathVariable Long auffuehrungId) {
        return statistikService.getEinnahmenProAuffuehrung(auffuehrungId);
    }

    @GetMapping("/einnahmen/film/{filmId}")
    public StatistikDTO getEinnahmenProFilm(@PathVariable Long filmId) {
        return statistikService.getEinnahmenProFilm(filmId);
    }
}
