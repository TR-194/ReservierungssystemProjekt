package com.kino.reservierungssystem.service;


import com.kino.reservierungssystem.dto.BuchungDTO;
import com.kino.reservierungssystem.mapper.BuchungMapper;
import com.kino.reservierungssystem.model.Buchung;
import com.kino.reservierungssystem.repository.BuchungRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuchungService {

    private final BuchungRepository buchungRepository;
    private final BuchungMapper buchungMapper;

    public BuchungService(BuchungRepository buchungRepository, BuchungMapper buchungMapper) {
        this.buchungRepository = buchungRepository;
        this.buchungMapper = buchungMapper;
    }

    public List<BuchungDTO> getAllBuchungen() {
        return buchungRepository.findAll().stream()
                .map(buchungMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public BuchungDTO createBuchung(BuchungDTO buchungDTO) {
        Buchung buchung = buchungRepository.save(buchungMapper.toEntity(buchungDTO));
        return buchungMapper.fromEntity(buchung);
    }

    public void deleteBuchung(Long id) {
        buchungRepository.deleteById(id);
    }
}