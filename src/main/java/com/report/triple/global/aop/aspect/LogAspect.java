package com.report.triple.global.aop.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class LogAspect {
    @Pointcut("execution(* com.report.triple.domain..*..*(..))")
    public void doLogPointCut(){};

    @Around("doLogPointCut()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMs = System.currentTimeMillis();
        log.info("[START] {} requestTime = {}", joinPoint.getSignature().toShortString(), LocalDateTime.now());

        Object result = joinPoint.proceed();

        long resultTimeMs = System.currentTimeMillis() - startTimeMs;
        log.info("[END] {} processingTime = {}ms", joinPoint.getSignature().toShortString(), resultTimeMs);

        return result;
    }
}
