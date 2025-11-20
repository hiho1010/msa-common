package org.bangbang.infrastructure.kafka.support;

import org.bangbang.infrastructure.kafka.annotation.KafkaClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.Set;

public class KafkaClientRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    @Override
    public void setEnvironment(@NotNull Environment environment) {
    }

    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata importingClassMetadata, @NotNull BeanDefinitionRegistry registry) {
        // 1. 스캐너 설정: 인터페이스도 찾을 수 있도록 설정 (기본값은 클래스만 찾음)
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isIndependent() && beanDefinition.getMetadata().isInterface();
            }
        };

        // @KafkaClient가 붙은 것만 찾음
        scanner.addIncludeFilter(new AnnotationTypeFilter(KafkaClient.class));

        // 2. 스캔할 패키지 결정 (메인 애플리케이션 패키지 기준)
        Set<String> basePackages = getBasePackages(importingClassMetadata);

        // 3. 스캔 및 빈 등록
        for (String basePackage : basePackages) {
            for (BeanDefinition candidate : scanner.findCandidateComponents(basePackage)) {
                registerKafkaClient(registry, candidate);
            }
        }
    }

    private void registerKafkaClient(BeanDefinitionRegistry registry, BeanDefinition candidate) {
        String interfaceName = candidate.getBeanClassName();

        // KafkaClientFactoryBean을 사용하여 빈 정의 생성
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(KafkaClientFactoryBean.class);
        builder.addPropertyValue("interfaceType", interfaceName);
        builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE); // StreamBridge 자동 주입

        // 빈 등록 (이름은 인터페이스 이름 그대로 사용)
        assert interfaceName != null;
        registry.registerBeanDefinition(interfaceName, builder.getBeanDefinition());
    }

    private Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        // 실제 구현 시에는 @EnableKafkaClients 어노테이션 속성에서 패키지를 읽어오거나,
        // 호출한 클래스의 패키지를 기본값으로 사용합니다.
        // 여기서는 간단히 현재 실행 중인 메인 클래스의 패키지를 쓴다고 가정하거나 고정값을 쓸 수 있습니다.
        // *실무 팁: ClassUtils.getPackageName(importingClassMetadata.getClassName()) 사용
        return Set.of(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
    }
}