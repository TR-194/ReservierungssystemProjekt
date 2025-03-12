package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.KinosaalDTO;
import com.kino.reservierungssystem.mapper.KinosaalMapper;
import com.kino.reservierungssystem.model.Kinosaal;
import com.kino.reservierungssystem.repository.KinosaalRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KinosaalConsumer {

    private final KinosaalRepository kinosaalRepository;
    private final KinosaalMapper kinosaalMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KinosaalConsumer(KinosaalRepository kinosaalRepository, KinosaalMapper kinosaalMapper, KafkaTemplate<String, Object> kafkaTemplate) {
        this.kinosaalRepository = kinosaalRepository;
        this.kinosaalMapper = kinosaalMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     *  Holt alle Kinosäle und sendet sie über Kafka zurück.
     */
    @KafkaListener(topics = "kinosaal.getAll", groupId = "kino-group")
    public void getAllKinosaele(String requestId) {
        List<KinosaalDTO> kinosaele = kinosaalRepository.findAll().stream()
                .map(kinosaalMapper::fromEntity)
                .collect(Collectors.toList());

        kafkaTemplate.send("kino.response", Map.of("requestId", requestId, "data", kinosaele));
    }

    /**
     *  Holt einen Kinosaal nach ID und sendet ihn zurück.
     */
    @KafkaListener(topics = "kinosaal.getById", groupId = "kino-group")
    public void getKinoSaalById(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long id = Long.valueOf(request.get("data").toString());

        Kinosaal kinosaal = kinosaalRepository.findById(id).orElse(null);
        KinosaalDTO kinosaalDTO = kinosaal != null ? kinosaalMapper.fromEntity(kinosaal) : null;

        kafkaTemplate.send("kino.response", Map.of("requestId", requestId, "data", kinosaalDTO));
    }

    /**
     *  Erstellt einen neuen Kinosaal und speichert ihn in der DB.
     */
    @KafkaListener(topics = "kinosaal.create", groupId = "kino-group")
    public void createKinosaal(KinosaalDTO kinosaalDTO) {
        Kinosaal kinosaal = kinosaalMapper.toEntity(kinosaalDTO);
        kinosaal = kinosaalRepository.save(kinosaal);
    }

    /**
     *  Aktualisiert einen Kinosaal.
     */
    @KafkaListener(topics = "kinosaal.update", groupId = "kino-group")
    public void updateKinosaal(KinosaalDTO kinosaalDTO) {
        Kinosaal kinosaal = kinosaalMapper.toEntity(kinosaalDTO);
        kinosaalRepository.save(kinosaal);
    }

    /**
     *  Löscht einen Kinosaal aus der Datenbank.
     */
    @KafkaListener(topics = "kinosaal.delete", groupId = "kino-group")
    public void deleteKinosaal(Long id) {
        kinosaalRepository.deleteById(id);
    }
}
