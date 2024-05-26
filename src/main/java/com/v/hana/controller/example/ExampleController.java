package com.v.hana.controller.example;

import com.v.hana.command.example.ExampleCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.example.ExampleRequest;
import com.v.hana.dto.example.ExampleResponse;
import com.v.hana.service.example.ExampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "ExampleController", description = "예시 컨트롤러 클래스")
@RestController
@RequestMapping("v1/api")
public class ExampleController {
    private final ExampleService exampleService;

    @MethodInfo(name = "example", description = "예시 컨트롤러의 예시 메소드를 실행합니다.")
    @GetMapping("/example")
    public ResponseEntity<ExampleResponse> exampleGET(ExampleRequest exampleRequest) {
        return ResponseEntity.ok(
                ExampleResponse.builder()
                        .response(
                                exampleService.example(
                                        ExampleCommand.builder()
                                                .request(exampleRequest.getRequest())
                                                .build()))
                        .build());
    }

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }
}
