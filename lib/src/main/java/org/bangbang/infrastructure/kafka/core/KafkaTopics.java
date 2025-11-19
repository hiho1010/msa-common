package org.bangbang.infrastructure.kafka.core;

public final class KafkaTopics {

    private KafkaTopics() {}

    // 예시: 유저 관련
    public static final String USER_CREATED = "user.created";
    public static final String USER_DELETED = "user.deleted";

    // 예시: 외부 API 요청/응답
    public static final String EXTERNAL_API_REQUEST  = "external.api.request";
    public static final String EXTERNAL_API_RESPONSE = "external.api.response";

    // 필요 시 Aggregate service용 토픽들 추가
}