package com.muslimov.vlad.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "common.logging")
public record LoggingProperties(boolean disabled) {
}
