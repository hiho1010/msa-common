package org.bangbang.infrastructure.kafka.annotation;

import org.bangbang.infrastructure.kafka.support.KafkaClientRegistrar;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(KafkaClientRegistrar.class) // 여기서 레지스트라를 로딩!
public @interface EnableKafkaClients {
    String[] basePackages() default {};
}