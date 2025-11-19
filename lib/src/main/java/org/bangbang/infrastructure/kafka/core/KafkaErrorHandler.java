package org.bangbang.infrastructure.kafka.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.messaging.Message;

@Slf4j
public class KafkaErrorHandler implements CommonErrorHandler {

    @Override
    public void handleRecord(
        Exception e,
        ConsumerRecord<?, ?> record,
        Consumer<?, ?> consumer,
        Message<?> message
    ) {
        log.error(
            "[Kafka] Error while processing record. topic={}, partition={}, offset={}, value={}",
            record.topic(),
            record.partition(),
            record.offset(),
            record.value(),
            e
        );

        // 필요하면 여기서 DLQ로 보내거나, 특정 예외에 대해 seek 등 커스텀 로직 추가
    }
}