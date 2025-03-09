package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.model.Auffuehrung;
import com.kino.reservierungssystem.repository.AuffuehrungRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuffuehrungService {
    private final AuffuehrungRepository auffuehrungRepository;

    public AuffuehrungService(AuffuehrungRepository auffuehrungRepository) {
        this.auffuehrungRepository = auffuehrungRepository;
    }

    public List<Auffuehrung> getAllAuffuehrungen() {
        return auffuehrungRepository.findAll();
    }

    public Auffuehrung getAuffuehrungById(Long id) {
        return auffuehrungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auffuehrung not found"));
    }

    public Auffuehrung saveAuffuehrung(Auffuehrung auffuehrung) {
        return auffuehrungRepository.save(auffuehrung);
    }

    public Auffuehrung updateAuffuehrung(Long id, Auffuehrung auffuehrungDetails) {
        Auffuehrung auffuehrung = auffuehrungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auffuehrung not found"));
        auffuehrung.setDatum(auffuehrungDetails.getDatum());
        auffuehrung.setUhrzeit(auffuehrungDetails.getUhrzeit());
        auffuehrung.setFilm(auffuehrungDetails.getFilm());
        auffuehrung.setKinosaal(auffuehrungDetails.getKinosaal());
        auffuehrung.setPreismodell(auffuehrungDetails.getPreismodell());
        return auffuehrungRepository.save(auffuehrung);
    }

    public void deleteAuffuehrung(Long id) {
        auffuehrungRepository.deleteById(id);
    }
}