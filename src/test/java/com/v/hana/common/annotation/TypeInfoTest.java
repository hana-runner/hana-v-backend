package com.v.hana.common.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@TypeInfo(name = "ExampleClass", description = "예시 클래스")
class ExampleClass {}

@TypeInfo(name = "TypeInfoTest", description = "TypeInfo 어노테이션 테스트")
@SpringBootTest
public class TypeInfoTest {

    @MethodInfo(name = "testTypeInfo", description = "TypeInfo 어노테이션을 테스트합니다.")
    @Test
    public void testTypeInfoAnnotation() throws NoSuchMethodException {
        Class<?> clazz = ExampleClass.class;
        assertNotNull(clazz.getAnnotation(TypeInfo.class), "TypeInfo 어노테이션이 존재하지 않습니다.");

        TypeInfo typeInfoAnnotation = clazz.getAnnotation(TypeInfo.class);
        assertEquals("ExampleClass", typeInfoAnnotation.name(), "클래스 이름이 일치하지 않습니다.");
        assertEquals("예시 클래스", typeInfoAnnotation.description(), "클래스 설명이 일치하지 않습니다.");
    }
}
