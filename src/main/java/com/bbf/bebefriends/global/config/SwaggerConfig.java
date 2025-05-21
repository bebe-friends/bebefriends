package com.bbf.bebefriends.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI 기본 설정 (Bearer 토큰 적용)
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // Bearer 토큰 보안 스키마 설정
        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Bearer 인증 필요 설정
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("BearerAuth");

        return new OpenAPI()
                .info(new Info() // API 문서의 기본 정보
                        .title("Bebe Friends API")
                        .description("베베 프랜즈 백엔드 API 문서")
                        .version("v1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", bearerAuthScheme))
                .addSecurityItem(securityRequirement);
    }

    /**
     * GroupedOpenApi를 통해 API를 그룹별로 문서화
     */
    private GroupedOpenApi buildGroupedOpenApi(String group, String basePackage) {
        return GroupedOpenApi.builder()
                .group(group)
                .pathsToMatch("/api/v1/**")
                .packagesToScan(basePackage)
                .build();
    }

    /**
     * 유저 관련 API
     */
    @Bean
    public GroupedOpenApi userApi() {
        return buildGroupedOpenApi("유저 관련 API @조정우", "com.bbf.bebefriends.member.controller");
    }

    /**
     * 푸시 알림 관련 API
     */
    @Bean
    public GroupedOpenApi notificationApi() {
        return buildGroupedOpenApi("푸시 알림 관련 API @조정우", "com.bbf.bebefriends.notification.controller");
    }

    /**
     * 커뮤니티 관련 API
     */
    @Bean
    public GroupedOpenApi communityApi() {
        return buildGroupedOpenApi("커뮤니티 관련 API @문호주", "com.bbf.bebefriends.community.controller");
    }

    /**
     * 핫딜 관련 API
     */
    @Bean
    public GroupedOpenApi hotdealApi() {
        return buildGroupedOpenApi("핫딜 관련 API @유석균", "com.bbf.bebefriends.hotdeal.controller");
    }
}