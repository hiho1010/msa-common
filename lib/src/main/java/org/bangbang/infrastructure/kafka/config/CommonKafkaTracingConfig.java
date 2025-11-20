package org.bangbang.infrastructure.kafka.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ContainerCustomizer;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

@AutoConfiguration
@ConditionalOnBean(ObservationRegistry.class) // 메인 앱에 관측 설정이 있을 때만 동작
public class CommonKafkaTracingConfig {

    /**
     * 카프카 리스너에 '관측(Observation)' 기능을 활성화합니다.
     * 이 설정이 켜져 있어야 카프카 헤더에 들어있는 'traceparent' 정보를 읽어서
     * 현재 서비스의 로그에 TraceId를 연동시킬 수 있습니다.
     */
    @Bean
    public ContainerCustomizer<Object, Object, AbstractMessageListenerContainer<Object, Object>> tracingContainerCustomizer() {
        return container -> {
            // 관측 기능 활성화
            container.getContainerProperties().setObservationEnabled(true);
        };
    }
}