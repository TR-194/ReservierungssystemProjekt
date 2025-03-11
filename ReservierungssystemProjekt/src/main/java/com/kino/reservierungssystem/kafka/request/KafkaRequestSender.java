package com.kino.reservierungssystem.kafka.request;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaRequestSender {

    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    public KafkaRequestSender(KafkaTemplate<String, Map<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRequest(String topic, String requestId, Object data) {
        Map<String, Object> message = Map.of("requestId", requestId, "data", data);
        kafkaTemplate.send(topic, message);
    }
}