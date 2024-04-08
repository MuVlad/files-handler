package com.muslimov.vlad.aop;

import com.muslimov.vlad.annotation.SensitiveData;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.StringJoiner;

@Aspect
public class LoggingAspect {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isService() {
    }

    @After("isService() && execution(public * *(..))")
    public void addLogging(JoinPoint point) {

        final var methodName = point.getSignature().getName();
        final var args = point.getArgs();
        final var method = ((MethodSignature) point.getSignature()).getMethod();
        final var joiner = new StringJoiner(
            ", ",
            methodName + " args: [",
            "]"
        );
        for (int i = 0; i < method.getParameterAnnotations().length; i++) {
            final boolean hasSensData = Arrays.stream(method.getParameterAnnotations()[i])
                .anyMatch(SensitiveData.class::isInstance);
            if (hasSensData) {
                joiner.add("***");
            } else {
                joiner.add(args[i] + "");
            }
        }
        final var className = point.getTarget().getClass().getName();
        Logger log = LoggerFactory.getLogger(className);
        log.info(joiner.toString());
    }
}