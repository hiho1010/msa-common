package org.bangbang.infrastructure.kafka.external;

public record ExternalApiRequest<T>(
    String requestId,            // 클라이언트가 관리하거나 UUID
    String sourceService,        // 요청한 서비스 이름
    ExternalApiType apiType,     // 어떤 외부 API인지
    T payload                    // API 요청에 필요한 데이터 (e.g. 주소)
) { }