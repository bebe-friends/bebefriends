package com.bbf.bebefriends.global.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
@SecurityScheme(
        name = "firebaseAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bebe Friends API")
                        .description("베베 프랜즈 백엔드 API 문서")
                        .version("v1.0.0"));
    }

//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("v1")
//                .pathsToMatch("/api/**")
//                .build();
//    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("유저 관련 API @조정우")
                .pathsToMatch("/api/v1/**")
                .packagesToScan("com.bbf.bebefriends.member.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi communityApi() {
        return GroupedOpenApi.builder()
                .group("커뮤니티 관련 API @문호주")
                .pathsToMatch("/api/v1/**")
                .packagesToScan("com.bbf.bebefriends.community.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi hotdealApi() {
        return GroupedOpenApi.builder()
                .group("핫딜 관련 API @유석균")
                .pathsToMatch("/api/v1/**")
                .packagesToScan("com.bbf.bebefriends.hotdeal.controller")
                .build();
    }

}
