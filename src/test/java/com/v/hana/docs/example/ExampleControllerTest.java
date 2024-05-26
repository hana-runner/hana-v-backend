package com.v.hana.docs.example;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.v.hana.command.example.ExampleCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.controller.example.ExampleController;
import com.v.hana.docs.RestDocsTest;
import com.v.hana.service.example.ExampleService;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

@TypeInfo(name = "ExampleControllerTest", description = "예시 컨트롤러 테스트 클래스")
public class ExampleControllerTest extends RestDocsTest {

    private final ExampleService exampleService = mock(ExampleService.class);

    @Override
    protected Object initializeController() {
        return new ExampleController(exampleService);
    }

    @MethodInfo(name = "exampleSuccessGET", description = "예시 API 성공을 테스트합니다.")
    @Test
    public void exampleSuccessGET() throws Exception {
        // Given
        given(exampleService.example(ExampleCommand.builder().request(23).build())).willReturn(23);

        // When & Then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/api/example")
                                .param("request", "23"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "exampleGET",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("example")
                                                .summary("예시 API")
                                                .description(
                                                        "예시 API 입니다. 요청 데이터를 받아 응답 데이터를 반환합니다. 0을 넣으면 Exception이 발생합니다.")
                                                .queryParameters(
                                                        parameterWithName("request")
                                                                .description("요청 데이터")
                                                                .type(SimpleType.NUMBER))
                                                .responseFields(
                                                        fieldWithPath("status")
                                                                .description("상태 코드")
                                                                .type(JsonFieldType.NUMBER),
                                                        fieldWithPath("success")
                                                                .description("성공 여부")
                                                                .type(JsonFieldType.BOOLEAN),
                                                        fieldWithPath("timestamp")
                                                                .description("응답 시간")
                                                                .type(JsonFieldType.STRING)
                                                                .attributes(
                                                                        key("format")
                                                                                .value(
                                                                                        "yyyy-MM-dd HH:mm:ss")),
                                                        fieldWithPath("code")
                                                                .description("응답 코드")
                                                                .type(JsonFieldType.STRING),
                                                        fieldWithPath("message")
                                                                .description("응답 메시지")
                                                                .type(JsonFieldType.STRING),
                                                        fieldWithPath("response")
                                                                .description("응답 데이터")
                                                                .type(JsonFieldType.NUMBER))
                                                .build())));
    }
}
