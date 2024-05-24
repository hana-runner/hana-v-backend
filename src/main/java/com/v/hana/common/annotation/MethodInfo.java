package com.v.hana.common.annotation;

import java.lang.annotation.*;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@TypeInfo(name = "MethodInfo", description = "MethodInfo 어노테이션 인터페이스")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MethodInfo {
    @AliasFor(annotation = Component.class)
    String value() default "";

    String name() default "";

    String description() default "";
}
