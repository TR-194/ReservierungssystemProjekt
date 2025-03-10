package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.dto.AuffuehrungDTO;
import com.kino.reservierungssystem.mapper.AuffuehrungMapper;
import com.kino.reservierungssystem.model.Auffuehrung;
import com.kino.reservierungssystem.repository.AuffuehrungRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuffuehrungService {

    private final AuffuehrungRepository auffuehrungRepository;
    private final AuffuehrungMapper auffuehrungMapper;

    public AuffuehrungService(AuffuehrungRepository auffuehrungRepository, AuffuehrungMapper auffuehrungMapper) {
        this.auffuehrungRepository = auffuehrungRepository;
        this.auffuehrungMapper = auffuehrungMapper;
    }

    public List<AuffuehrungDTO> getAllAuffuehrungen() {
        return auffuehrungRepository.findAll().stream()
                .map(auffuehrungMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public AuffuehrungDTO createAuffuehrung(AuffuehrungDTO auffuehrungDTO) {
        Auffuehrung auffuehrung = auffuehrungRepository.save(auffuehrungMapper.toEntity(auffuehrungDTO));
        return auffuehrungMapper.fromEntity(auffuehrung);
    }

    public void deleteAuffuehrung(Long id) {
        auffuehrungRepository.deleteById(id);
    }

    public AuffuehrungDTO getAuffuehrungById(Long id) {
        Auffuehrung auffuehrung = auffuehrungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auffuehrung not found with id: " + id));
        return auffuehrungMapper.fromEntity(auffuehrung);
    }
}