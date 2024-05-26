package com.v.hana.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TypeInfo(name = "RestDocsTest", description = "Rest Docs 테스트 클래스")
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsTest {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @MethodInfo(name = "setUp", description = "Rest Docs 설정을 초기화합니다.")
    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc =
                MockMvcBuilders.standaloneSetup(initializeController())
                        .apply(documentationConfiguration(restDocumentationContextProvider))
                        .build();
    }

    protected abstract Object initializeController();
}
