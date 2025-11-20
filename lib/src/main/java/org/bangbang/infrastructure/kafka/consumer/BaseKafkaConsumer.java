package org.bangbang.infrastructure.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import java.util.function.Consumer;

@Slf4j
public abstract class BaseKafkaConsumer<T> {

    /**
     * 서비스 개발자는 이 메서드만 구현하면 됩니다.
     */
    protected abstract void consume(T event);

    /**
     * 스프링 클라우드 스트림이 인식할 함수형 빈을 자동으로 생성합니다.
     * 서비스 개발자는 이 코드를 몰라도 됩니다.
     */
    public Consumer<T> kafkaListener() {
        return event -> {
            try {
                log.info("메시지 수신 시작: {}", event);
                consume(event); // 자식 클래스의 로직 실행
                log.info("메시지 처리 완료");
            } catch (Exception e) {
                log.error("메시지 처리 중 에러 발생", e);
                throw e; // 던져야 DLQ 처리 등 재시도 로직이 동작함
            }
        };
    }
}