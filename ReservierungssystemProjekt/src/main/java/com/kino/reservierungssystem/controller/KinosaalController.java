package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.KinosaalDTO;
import com.kino.reservierungssystem.service.KinosaalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kinosaele")
public class KinosaalController {

    private final KinosaalService kinosaalService;

    public KinosaalController(KinosaalService kinosaalService) {
        this.kinosaalService = kinosaalService;
    }

    @GetMapping
    public List<KinosaalDTO> getAllKinoSaele() {
        return kinosaalService.getAllKinosaele();
    }

    @GetMapping("/{id}")
    public KinosaalDTO getKinoSaalById(@PathVariable Long id) {
        return kinosaalService.getKinosaalById(id);
    }

    @PostMapping
    public KinosaalDTO createKinoSaal(@RequestBody KinosaalDTO kinosaalDTO) {
        return kinosaalService.createKinosaal(kinosaalDTO);
    }

    @PutMapping("/{id}")
    public KinosaalDTO updateKinoSaal(@PathVariable Long id, @RequestBody KinosaalDTO kinosaalDTO) {
        return kinosaalService.updateKinosaal(id, kinosaalDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteKinoSaal(@PathVariable Long id) {
        kinosaalService.deleteKinosaal(id);
    }
}