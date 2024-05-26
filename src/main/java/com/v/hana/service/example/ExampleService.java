package com.v.hana.service.example;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import org.springframework.stereotype.Service;

@TypeInfo(name = "ExampleService", description = "예시 서비스 클래스")
@Service
public class ExampleService {
    @MethodInfo(name = "example", description = "예시 서비스의 예시 메소드를 실행합니다.")
    public String example(String request) {
        return request;
    }
}
