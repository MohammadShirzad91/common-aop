package com.example.commonaop.logging;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {
    @PostConstruct
    public void init(){
        log.warn("aspect proxyTargetClass not yet initialized");
    }
    @Around("execution(public * *(..)) && @within(org.springframework.web.bind.annotation.RestController)")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("executing method " + joinPoint.getSignature().getName());
        long start = System.currentTimeMillis();
        log.info("service input: {}", joinPoint.getArgs()[0]);
        Object response = null;
        try {
            response = joinPoint.proceed();
        } catch (Throwable e) {
            log.info("service throws: " + e);
        }
        long end = System.currentTimeMillis();
        log.info("duration: {} ms", (end - start));
        log.info("service output: {}", response);
        return response;
    }
}
