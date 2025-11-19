package org.bangbang.infrastructure.exception;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.bangbang.infrastructure.exception.handler")
public class ExceptionHandlerAutoConfiguration {
}