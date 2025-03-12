package com.kino.reservierungssystem.kafka.producer;

import com.kino.reservierungssystem.dto.SitzplatzDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SitzplatzProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SitzplatzProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sendet eine Anfrage zur Abfrage aller Sitzplätze.
     */
    public void sendSitzplatzAnfrage(String requestId) {
        kafkaTemplate.send("sitzplatz.getAll", requestId, "GET_ALL");
    }

    /**
     * Sendet eine Anfrage zur Abfrage eines bestimmten Sitzplatzes per ID.
     */
    public void sendSitzplatzByIdAnfrage(String requestId, Long sitzplatzId) {
        kafkaTemplate.send("sitzplatz.getById", requestId, sitzplatzId);
    }

    /**
     * Sendet eine Anfrage zur Aktualisierung des Sitzplatzstatus.
     */
    public void sendSitzplatzStatusUpdate(String requestId, SitzplatzDTO sitzplatzDTO) {
        kafkaTemplate.send("sitzplatz.updateStatus", requestId, sitzplatzDTO);
    }

    public void sendVerfuegbareSitzplaetze() {
        kafkaTemplate.send("sitzplatz.getVerfügbare", null);
    }

    public void sendSitzplaetzeByAuffuehrung(Long auffuehrungId) {
        kafkaTemplate.send("sitzplatz.getByAuffuehrung", auffuehrungId);
    }

    public void sendSitzplaetzeByKinosaal(Long kinosaalId) {
        kafkaTemplate.send("sitzplatz.getByKinosaal", kinosaalId);
    }
}

