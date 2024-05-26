package com.v.hana.controller.example;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.response.BaseResponse;
import com.v.hana.dto.example.ExampleRequestDto;
import com.v.hana.dto.example.ExampleResponseDto;
import com.v.hana.service.example.ExampleService;
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
    public BaseResponse<ExampleResponseDto> exampleGET(ExampleRequestDto exampleRequestDto) {
        return BaseResponse.ok(
                ExampleResponseDto.builder()
                        .response(exampleService.example(exampleRequestDto.getRequest()))
                        .build());
    }

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }
}
