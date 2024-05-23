package com.v.hana.common.annotation;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExampleClassWithMethod {
    @MethodInfo(name = "ExampleMethod", description = "예시 메소드를 설명합니다.")
    public void ExampleMethod() {}
}

@TypeInfo(name = "MethodInfoTest", description = "MethodInfo 어노테이션 테스트")
@SpringBootTest
public class MethodInfoTest {

    @MethodInfo(name = "testMethodInfo", description = "MethodInfo 어노테이션을 테스트합니다.")
    @Test
    public void testMethodInfoAnnotation() throws NoSuchMethodException {
        Method method = ExampleClassWithMethod.class.getMethod("ExampleMethod");
        assertNotNull(method.getAnnotation(MethodInfo.class), "MethodInfo 어노테이션이 존재하지 않습니다.");

        MethodInfo methodInfoAnnotation = method.getAnnotation(MethodInfo.class);
        assertEquals("ExampleMethod", methodInfoAnnotation.name(), "메소드 이름이 일치하지 않습니다.");
        assertEquals("예시 메소드를 설명합니다.", methodInfoAnnotation.description(), "메소드 설명이 일치하지 않습니다.");
    }
}
