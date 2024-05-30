package com.v.hana.controller.user;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.docs.RestDocsTest;
import com.v.hana.dto.user.UserJoinRequest;
import com.v.hana.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@TypeInfo(name = "ExampleControllerTest", description = "예시 컨트롤러 테스트 클래스")
public class UserControllerTest extends RestDocsTest {

    private final UserService userService = mock(UserService.class);

    @Override
    protected Object initializeController() {
        return new UserController(userService);
    }

    @MethodInfo(name = "userJoinSuccessPOST", description = "회원 가입 API 성공을 테스트합니다.")
    @Test
    public void testJoinUser() throws Exception {
        UserJoinRequest userJoinRequest =
                UserJoinRequest.builder()
                        .username("username")
                        .name("name")
                        .pw("password")
                        .email("email@example.com")
                        .gender(1)
                        .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userJoinRequest);

        ResultActions result =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody));

        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "userJoinPOST",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("user")
                                                .summary("유저 회원 가입 API")
                                                .description("유저 회원 가입 API입니다. 요청 데이터를 받아 응답 데이터를 반환합니다. 0을 넣으면 Exception이 발생합니다.")
                                                .requestFields(
                                                        fieldWithPath("username").description("사용자 이름").type(JsonFieldType.STRING),
                                                        fieldWithPath("pw").description("비밀번호").type(JsonFieldType.STRING),
                                                        fieldWithPath("name").description("이름").type(JsonFieldType.STRING),
                                                        fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                                                        fieldWithPath("gender").description("성별").type(JsonFieldType.NUMBER))
                                                .responseFields(
                                                        fieldWithPath("status").description("상태 코드").type(JsonFieldType.NUMBER),
                                                        fieldWithPath("success").description("성공 여부").type(JsonFieldType.BOOLEAN),
                                                        fieldWithPath("timestamp").description("응답 시간").type(JsonFieldType.STRING)
                                                                .attributes(key("format").value("yyyy-MM-dd HH:mm:ss")),
                                                        fieldWithPath("code").description("응답 코드").type(JsonFieldType.STRING),
                                                        fieldWithPath("message").description("응답 메시지").type(JsonFieldType.STRING),
                                                        fieldWithPath("response").description("응답 데이터").type(JsonFieldType.NUMBER))
                                                .build())));
    }
}