package org.bangbang.infrastructure.kafka.core;

import java.util.List;
import org.apache.kafka.common.header.Header;
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

    @Override
    public void send(String topic, String key, Object payload, List<Header> headers) {
        kafkaTemplate.send(topic, key, payload, headers);
    }
}