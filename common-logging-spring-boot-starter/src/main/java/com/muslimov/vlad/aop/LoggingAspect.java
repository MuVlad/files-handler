package com.muslimov.vlad.aop;

import com.muslimov.vlad.annotation.SensitiveData;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isService() {
    }

    @After("isService() && execution(public **(..))")
    public void addLogging(JoinPoint point) {

        final var methodName = point.getSignature().getName();
        final var args = point.getArgs();

        StringBuilder logMessage = new StringBuilder()
            .append(methodName)
            .append(" args: [");

        for (Object arg : args) {
            if (!arg.getClass().isAnnotationPresent(SensitiveData.class)) {
                logMessage.append(arg).append(", ");
            }
        }
        if (args.length > 0) {
            logMessage.delete(logMessage.length() - 2, logMessage.length());
        }

        logMessage.append("]");

        final var className = point.getTarget().getClass().getName();
        Logger log = LoggerFactory.getLogger(className);
        log.info(logMessage.toString());
    }
}