package org.bangbang.infrastructure.kafka.external;

public record ExternalApiResponse<R>(
    String requestId,
    String sourceService,
    ExternalApiType apiType,
    boolean success,
    R payload,
    String errorCode,
    String errorMessage
) { }