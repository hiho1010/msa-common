package org.bangbang.infrastructure.kafka;

import java.time.Instant;
import java.util.Map;

public record KafkaMessage<T> (
    String topic,
    T payload,
    Map<String, Object> headers,
    Instant createdAt
) {
    public static <T> KafkaMessage<T> of(String key, T payload) {
        return new KafkaMessage<>(key, payload, Map.of(), Instant.now());
    }
}
