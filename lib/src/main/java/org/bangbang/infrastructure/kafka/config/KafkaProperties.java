package org.bangbang.infrastructure.kafka.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bangbang.kafka")
public record KafkaProperties(
    String bootstrapServers,
    String clientId,
    String consumerGroupPrefix
) {
}