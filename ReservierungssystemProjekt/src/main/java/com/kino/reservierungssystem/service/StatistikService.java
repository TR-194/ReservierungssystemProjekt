package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.dto.StatistikDTO;
import com.kino.reservierungssystem.repository.BuchungRepository;
import org.springframework.stereotype.Service;

@Service
public class StatistikService {

    private final BuchungRepository buchungRepository;

    public StatistikService(BuchungRepository buchungRepository) {
        this.buchungRepository = buchungRepository;
    }

    public StatistikDTO getEinnahmenProAuffuehrung(Long auffuehrungId) {
        Double sum = buchungRepository.sumEinnahmenByAuffuehrung(auffuehrungId);
        return new StatistikDTO(sum != null ? sum : 0.0);
    }

    public StatistikDTO getEinnahmenProFilm(Long filmId) {
        Double sum = buchungRepository.sumEinnahmenByFilm(filmId);
        return new StatistikDTO(sum != null ? sum : 0.0);
    }
}