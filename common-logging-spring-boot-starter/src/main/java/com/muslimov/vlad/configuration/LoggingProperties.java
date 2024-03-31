package com.muslimov.vlad.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "common.logging")
public record LoggingProperties(@DefaultValue("false") boolean disabled) {
}
