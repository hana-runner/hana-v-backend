package com.v.hana;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.config.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TypeInfo(name = "ApplicationTests", description = "어플리케이션 테스트 클래스")
@SpringBootTest(classes = Application.class)
@ExtendWith({MySQLTestConfig.class, RedisTestConfig.class})
@TestPropertySource(locations = "classpath:application.yml")
class ApplicationTests {

    @MethodInfo(name = "contextLoads", description = "어플리케이션 컨텍스트가 정상적으로 로드되는지 확인합니다.")
    @Test
    void contextLoads() {}
}
