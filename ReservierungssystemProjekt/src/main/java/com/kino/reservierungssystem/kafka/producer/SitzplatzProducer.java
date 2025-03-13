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
        kafkaTemplate.send("sitzplatzGetAll", requestId, "GET_ALL");
    }

    /**
     * Sendet eine Anfrage zur Abfrage eines bestimmten Sitzplatzes per ID.
     */
    public void sendSitzplatzByIdAnfrage(String requestId, Long sitzplatzId) {
        kafkaTemplate.send("sitzplatzGetById", requestId, sitzplatzId);
    }

    /**
     * Sendet eine Anfrage zur Aktualisierung des Sitzplatzstatus.
     */
    public void sendSitzplatzStatusUpdate(String requestId, SitzplatzDTO sitzplatzDTO) {
        kafkaTemplate.send("sitzplatzUpdateStatus", requestId, sitzplatzDTO);
    }

    public void sendVerfuegbareSitzplaetze() {
        kafkaTemplate.send("sitzplatzGetVerfügbare", null);
    }

    public void sendSitzplaetzeByAuffuehrung(Long auffuehrungId) {
        kafkaTemplate.send("sitzplatzGetByAuffuehrung", auffuehrungId);
    }

    public void sendSitzplaetzeByKinosaal(Long kinosaalId) {
        kafkaTemplate.send("sitzplatzGetByKinosaal", kinosaalId);
    }
}

