package org.bangbang.infrastructure.kafka.core;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class KafkaMessage<T> {

    private final String messageId;     // UUID
    private final String correlationId; // 요청-응답 매칭용 (없다면 messageId 재사용)
    private final String sourceService; // 보낸 서비스 이름
    private final String type;          // 이벤트 타입 (e.g. USER_CREATED)
    private final Instant sentAt;       // 보낸 시각
    private final T payload;            // 실제 데이터

    public static <T> KafkaMessage<T> of(String sourceService, String type, T payload) {
        String msgId = UUID.randomUUID().toString();
        return KafkaMessage.<T>builder()
            .messageId(msgId)
            .correlationId(msgId)
            .sourceService(sourceService)
            .type(type)
            .sentAt(Instant.now())
            .payload(payload)
            .build();
    }

    public static <T> KafkaMessage<T> of(
        String sourceService,
        String type,
        String correlationId,
        T payload
    ) {
        return KafkaMessage.<T>builder()
            .messageId(UUID.randomUUID().toString())
            .correlationId(correlationId)
            .sourceService(sourceService)
            .type(type)
            .sentAt(Instant.now())
            .payload(payload)
            .build();
    }
}