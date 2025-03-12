package com.example.KinoFinances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class KafkaHandler {

    DbAccess dbAccess = new DbAccess();

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, Map<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    public void sendResponse(String topic, String requestId, Object data) {
        Map<String, Object> message = Map.of("requestId", requestId, "data", data);
        kafkaTemplate.send(topic, message);
    }

    public KafkaHandler(KafkaTemplate<String, Map<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "zahlung.create", groupId = "kino-group")
    public void handleZahlungCreateRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        ZahlungDTO zahlungDTO = (ZahlungDTO) request.get("zahlungDTO");
        Payment payment = new Payment(zahlungDTO.getBuchungId().longValue(), "TODO: Add Film to ZahlungDTO", zahlungDTO.getBetrag(), zahlungDTO.getZahlungsdatum()); //TODO: Replace by AuffuehrungsId!!!
        dbAccess.SavePayment(payment);
        sendResponse("kino.response", requestId, true);
    }

    @KafkaListener(topics = "zahlung.getAll", groupId = "kino-group")
    public void handleZahlungGetAllRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<Payment> payments = dbAccess.GetAllPayments();
        sendResponse("kino.response", requestId, payments);
    }
}
