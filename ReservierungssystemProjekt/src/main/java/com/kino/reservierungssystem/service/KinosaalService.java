package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.dto.KinosaalDTO;
import com.kino.reservierungssystem.mapper.KinosaalMapper;
import com.kino.reservierungssystem.model.Kinosaal;
import com.kino.reservierungssystem.repository.KinosaalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KinosaalService {

    private final KinosaalRepository kinosaalRepository;
    private final KinosaalMapper kinosaalMapper;

    public KinosaalService(KinosaalRepository kinosaalRepository, KinosaalMapper kinosaalMapper) {
        this.kinosaalRepository = kinosaalRepository;
        this.kinosaalMapper = kinosaalMapper;
    }

    public List<KinosaalDTO> getAllKinosaele() {
        return kinosaalRepository.findAll().stream()
                .map(kinosaalMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public KinosaalDTO getKinosaalById(Long id) {
        return kinosaalRepository.findById(id)
                .map(kinosaalMapper::fromEntity)
                .orElseThrow(() -> new RuntimeException("Kinosaal nicht gefunden"));
    }

    public KinosaalDTO createKinosaal(KinosaalDTO kinosaalDTO) {
        Kinosaal kinosaal = kinosaalRepository.save(kinosaalMapper.toEntity(kinosaalDTO));
        return kinosaalMapper.fromEntity(kinosaal);
    }

    public KinosaalDTO updateKinosaal(Long id, KinosaalDTO kinosaalDTO) {
        Kinosaal kinosaal = kinosaalMapper.toEntity(kinosaalDTO);
        kinosaal.setId(id);
        return kinosaalMapper.fromEntity(kinosaalRepository.save(kinosaal));
    }

    public void deleteKinosaal(Long id) {
        kinosaalRepository.deleteById(id);
    }
}