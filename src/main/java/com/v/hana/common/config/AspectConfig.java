package com.v.hana.common.config;

import com.v.hana.common.annotation.TypeInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@TypeInfo(name = "AspectConfig", description = "에스펙트 설정 클래스")
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {}
