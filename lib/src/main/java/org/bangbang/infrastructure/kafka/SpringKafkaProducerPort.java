package org.bangbang.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;

public class SpringKafkaProducerPort implements KafkaProducerPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SpringKafkaProducerPort(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public <T> void send(String topic, T payload) {
        kafkaTemplate.send(topic, payload);
    }

    @Override
    public <T> void send(String topic, String key, T payload) {
        kafkaTemplate.send(topic, key, payload);
    }
}