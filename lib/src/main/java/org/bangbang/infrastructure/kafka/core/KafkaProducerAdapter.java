package org.bangbang.infrastructure.kafka.core;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerAdapter implements KafkaProducerPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(String topic, Object payload) {
        log.debug("[Kafka] send topic={}, payload={}", topic, payload);
        kafkaTemplate.send(topic, payload);
    }

    @Override
    public void send(String topic, String key, Object payload) {
        log.debug("[Kafka] send topic={}, key={}, payload={}", topic, key, payload);
        kafkaTemplate.send(topic, key, payload);
    }

    @Override
    public void send(String topic, String key, Object payload, List<Header> headers) {
        ProducerRecord<String, Object> record =
            new ProducerRecord<>(topic, null, key, payload, headers);

        log.debug("[Kafka] send topic={}, key={}, payload={}, headers={}",
            topic, key, payload, headers);

        kafkaTemplate.send(record);
    }
}