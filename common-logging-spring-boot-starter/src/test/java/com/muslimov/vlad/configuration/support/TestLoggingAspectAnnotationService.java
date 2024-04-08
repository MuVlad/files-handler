package com.muslimov.vlad.configuration.support;

import com.muslimov.vlad.annotation.SensitiveData;
import org.springframework.stereotype.Service;

@Service
public class TestLoggingAspectAnnotationService {

    public void testMethod(String publicData, @SensitiveData String privateData) {
        System.out.println(publicData);
    }
}
