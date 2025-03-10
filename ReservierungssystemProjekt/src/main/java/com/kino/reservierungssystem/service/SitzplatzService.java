package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.dto.SitzplatzDTO;
import com.kino.reservierungssystem.mapper.SitzplatzMapper;
import com.kino.reservierungssystem.model.Sitzplatz;
import com.kino.reservierungssystem.model.Status;
import com.kino.reservierungssystem.repository.SitzplatzRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SitzplatzService {

    private final SitzplatzRepository sitzplatzRepository;
    private final SitzplatzMapper sitzplatzMapper;

    public SitzplatzService(SitzplatzRepository sitzplatzRepository, SitzplatzMapper sitzplatzMapper) {
        this.sitzplatzRepository = sitzplatzRepository;
        this.sitzplatzMapper = sitzplatzMapper;
    }

    public List<SitzplatzDTO> getAllSitzplaetze() {
        return sitzplatzRepository.findAll().stream()
                .map(sitzplatzMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public SitzplatzDTO updateSitzplatzStatus(Long id, String status) {
        Sitzplatz sitzplatz = sitzplatzRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden"));
        sitzplatz.setStatus(Status.valueOf(status.toUpperCase()));
        return sitzplatzMapper.fromEntity(sitzplatzRepository.save(sitzplatz));
    }
}