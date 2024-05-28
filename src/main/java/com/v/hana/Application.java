package com.v.hana;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@TypeInfo(name = "Application", description = "어플리케이션 클래스")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {

    @MethodInfo(name = "main", description = "어플리케이션을 실행합니다.")
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
