package com.v.hana.common.config;

import static org.springframework.context.support.AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/* 해당 부분은 스프링 프레임워크 스펙으로 정해진 이름대로 빈을 등록해야 함*/
@TypeInfo(name = "ApplicationEventConfig", description = "애플리케이션 이벤트 설정 클래스")
@Configuration
public class ApplicationEventConfig {
    @MethodInfo(name = "applicationEventMulticaster", description = "이벤트 Multicaster를 설정합니다.")
    @Bean(name = APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster =
                new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(asyncExecutor());
        return eventMulticaster;
    }

    @MethodInfo(name = "asyncExecutor", description = "비동기 이벤트 처리를 위한 Executor를 설정합니다.")
    private Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10000);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(10);
        executor.initialize();
        return executor;
    }
}
