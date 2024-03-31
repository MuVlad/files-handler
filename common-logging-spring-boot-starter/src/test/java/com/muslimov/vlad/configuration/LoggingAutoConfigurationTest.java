package com.muslimov.vlad.configuration;

import com.muslimov.vlad.aop.LoggingAspect;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggingAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class));

    @Test
    public void loggingAutoConfigurationCreatedWhenRequiredClassPresent() {
        contextRunner.withPropertyValues("common.logging.disabled=false")
            .run(context ->
                assertThat(context).hasSingleBean(LoggingAspect.class)
            );
    }

    @Test
    public void loggingAutoConfigurationNotCreatedWhenRequiredClassMissing() {
        contextRunner.withPropertyValues("common.logging.disabled=false")
            .withClassLoader(new FilteredClassLoader(LoggingAspect.class))
            .run(context ->
                assertThat(context).doesNotHaveBean(LoggingAutoConfiguration.class)
            );
    }

    @Test
    public void loggingAutoConfigurationCreatedWhenPropertyIsSet() {
        contextRunner.withPropertyValues("common.logging.disabled=true")
            .run(context ->
                assertThat(context).doesNotHaveBean(LoggingAutoConfiguration.class)
            );
    }
}