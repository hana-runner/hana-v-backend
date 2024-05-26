package com.v.hana.service.example;

import com.v.hana.command.example.ExampleCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.exception.example.ExampleCodeException;
import com.v.hana.usecase.example.ExampleUseCase;
import org.springframework.stereotype.Service;

@TypeInfo(name = "ExampleService", description = "예시 서비스 클래스")
@Service
public class ExampleService implements ExampleUseCase {
    @MethodInfo(name = "example", description = "예시 서비스의 예시 메소드를 실행합니다.")
    @Override
    public int example(ExampleCommand exampleCommand) {
        try {
            if (exampleCommand.getRequest() == 0) {
                throw new ExampleCodeException();
            }
        } catch (Exception e) {
            throw new ExampleCodeException();
        }
        return exampleCommand.getRequest();
    }
}
