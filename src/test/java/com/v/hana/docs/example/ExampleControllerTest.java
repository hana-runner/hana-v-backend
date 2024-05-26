package com.v.hana.docs.example;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
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

    @MethodInfo(name = "exampleGET", description = "예시 API를 테스트합니다.")
    @Test
    public void exampleGET() throws Exception {
        // Given
        given(exampleService.example("test")).willReturn("test");

        // When & Then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/api/example")
                                .param("request", "test"))
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
                                                .description("예시 API 입니다.")
                                                .queryParameters(
                                                        parameterWithName("request")
                                                                .description("요청 데이터"))
                                                .responseFields(
                                                        fieldWithPath("success")
                                                                .description("성공 여부")
                                                                .type(JsonFieldType.BOOLEAN),
                                                        fieldWithPath("timestamp")
                                                                .description("응답 시간")
                                                                .type(JsonFieldType.STRING),
                                                        fieldWithPath("status")
                                                                .description("상태 코드")
                                                                .type(JsonFieldType.NUMBER),
                                                        fieldWithPath("code")
                                                                .description("응답 코드")
                                                                .type(JsonFieldType.STRING),
                                                        fieldWithPath("message")
                                                                .description("메시지")
                                                                .type(JsonFieldType.STRING),
                                                        fieldWithPath("data.response")
                                                                .description("응답 데이터"))
                                                .build())));
    }
}
