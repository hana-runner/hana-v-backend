package com.v.hana.common.config;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TypeInfo(name = "RedisTestConfig", description = "Redis 테스트 설정 클래스")
@Configuration
@Testcontainers
@EnableRedisRepositories(
        enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class RedisTestConfig implements BeforeAllCallback {
    @Container
    private static final GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:7.2.4")).withExposedPorts(6379);

    @MethodInfo(name = "beforeAll", description = "테스트 실행 전 Redis 컨테이너를 실행합니다.")
    @Override
    public void beforeAll(ExtensionContext context) {
        if (!redis.isRunning()) {
            redis.start();
        }

        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", String.valueOf(redis.getMappedPort(6379)));
        System.setProperty("spring.data.redis.password", "");
    }
}
