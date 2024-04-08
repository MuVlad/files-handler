package com.muslimov.vlad.configuration;

import com.muslimov.vlad.configuration.support.FilesHandlerSpringBootStarterTestApplication;
import com.muslimov.vlad.configuration.support.TestLoggingAspectAnnotationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
    classes = FilesHandlerSpringBootStarterTestApplication.class,
    properties = "common.logging.disabled=false"
)
public class LoggingAspectTest {

    @Autowired
    TestLoggingAspectAnnotationService service;

    @Test
    void test(CapturedOutput output) {
        service.testMethod("public data", "private data");
        assertThat(output).contains("testMethod args: [public data, ***]");
    }
}
