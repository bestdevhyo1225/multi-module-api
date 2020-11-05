package com.hyoseok.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class TimeTraceAop {

    @Around("execution(* com.hyoseok.api..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long resultTime = endTime - startTime;
            log.info("END TIME : " + joinPoint.toString() + " " + resultTime + "ms");
        }
    }

}
