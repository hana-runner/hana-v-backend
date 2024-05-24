package com.v.hana.common.aspect;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@TypeInfo(name = "LoggerAspect", description = "메소드 로깅 에스펙트 클래스")
@Aspect
@Component
public class LoggerAspect {

    private static final Logger LOGGER =
            org.apache.logging.log4j.LogManager.getLogger(LoggerAspect.class);

    @MethodInfo(name = "logMethodEntry", description = "메소드가 시작되는 시점에 로깅을 수행합니다.")
    @Before("@annotation(methodInfo)")
    public void logMethodEntry(JoinPoint joinPoint, MethodInfo methodInfo) {
        LOGGER.info(
                "'{}' 클래스의 '{}' 메소드가 시작됩니다. {}",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                methodInfo.description());
    }

    @MethodInfo(name = "logMethodExit", description = "메소드가 종료되는 시점에 로깅을 수행합니다.")
    @After("@annotation(methodInfo)")
    public void logMethodExit(JoinPoint joinPoint, MethodInfo methodInfo) {
        LOGGER.info(
                "'{}' 클래스의 '{}' 메소드가 종료됩니다. {}",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                methodInfo.description());
    }

    @MethodInfo(name = "logMethodException", description = "메소드 실행 중 예외 발생 시 로깅을 수행합니다.")
    @AfterThrowing(pointcut = "@annotation(methodInfo)", throwing = "exception")
    public void logMethodException(
            JoinPoint joinPoint, MethodInfo methodInfo, Throwable exception) {
        LOGGER.error(
                "'{}' 클래스의 '{}' 메소드 실행 중 예외 발생: {}. 예외 메시지: {}",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                methodInfo.description(),
                exception.getMessage(),
                exception);
    }
}
