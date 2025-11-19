package org.bangbang.infrastructure.kafka;

public interface KafkaMessageHandler<T> {
    void handle(T payload);

}
