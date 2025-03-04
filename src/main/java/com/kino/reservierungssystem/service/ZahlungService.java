package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.model.Zahlung;
import com.kino.reservierungssystem.repository.ZahlungRepository;
import org.springframework.stereotype.Service;

@Service
public class ZahlungService {
    private final ZahlungRepository zahlungRepository;

    public ZahlungService(ZahlungRepository zahlungRepository) {
        this.zahlungRepository = zahlungRepository;
    }

    public Zahlung saveZahlung(Zahlung zahlung) {
        return zahlungRepository.save(zahlung);
    }
}
