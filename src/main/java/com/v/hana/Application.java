package com.v.hana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        String activeProfile = System.getenv("SPRING_PROFILES_ACTIVE");

        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setAdditionalProfiles(activeProfile);

        springApplication.run(args);
    }
}
