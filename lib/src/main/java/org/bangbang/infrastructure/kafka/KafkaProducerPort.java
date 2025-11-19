package org.bangbang.infrastructure.kafka;

public interface KafkaProducerPort {
    <T> void send(String topic, T payload);
    <T> void send(String topic, String key, T payload);
}
