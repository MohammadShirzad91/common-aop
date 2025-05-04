package com.example.commonaop.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(1)
public class ControllerLoggingAspect {
    @Around("execution(public * *(..)) && @within(org.springframework.web.bind.annotation.RestController)")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("executing method " + joinPoint.getSignature().getName());
        long start = System.currentTimeMillis();
        log.info("service input: " + joinPoint.getArgs());
        Object response = null;
        try {
            response = joinPoint.proceed();
        } catch (Throwable e) {
            log.info("service throws: " + e);
        }
        long end = System.currentTimeMillis();
        log.info("duration: {} ms", (end - start));
        log.info("service output: " + joinPoint.getSignature().getName());
        return response;
    }
}
