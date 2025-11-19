package org.bangbang.infrastructure.kafka.core;

import java.util.List;
import org.apache.kafka.common.header.Header;

public interface KafkaProducerPort {

    void send(String topic, Object payload);

    void send(String topic, String key, Object payload);

    void send(String topic, String key, Object payload, List<Header> headers);
}