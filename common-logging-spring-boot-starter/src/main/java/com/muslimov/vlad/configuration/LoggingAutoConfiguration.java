package com.muslimov.vlad.configuration;

import com.muslimov.vlad.aop.LoggingAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(LoggingAspect.class)
@ConditionalOnProperty(prefix = "common.logging", name = "disabled", havingValue = "false")
public class LoggingAutoConfiguration {

    @Bean
    public LoggingAspect loggingAspect() {
        log.info("Initialize LoggingAspect");
        return new LoggingAspect();
    }
}
