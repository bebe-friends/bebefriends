package com.bbf.bebefriends.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String AUTH_ENDPOINTS = "/api/users/auth/**";
    private static final String FCM_TOKEN_ENDPOINTS = "/api/users/fcm-token";
    private static final String ADMIN_ENDPOINTS = "/api/admin/**";
    private static final String ROLE_ADMIN = "ADMIN";

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(this::configureAuthorization)
                .addFilterBefore(new FirebaseAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void configureAuthorization(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize)
    {
        authorize
                .requestMatchers(AUTH_ENDPOINTS).permitAll() // 로그인/회원가입 엔드포인트는 허용
                .requestMatchers(FCM_TOKEN_ENDPOINTS).authenticated() // fcm 토큰도 인증 이후 갱신
                .requestMatchers(ADMIN_ENDPOINTS).hasRole(ROLE_ADMIN); // 관리자 권한만 접근 가능

                if ("prod".equals(activeProfile)) {
                    authorize
                            .requestMatchers(
                                    "/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html"
                            ).denyAll();
                } else {
                    authorize
                            .requestMatchers(
                                    "/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html"
                            ).permitAll();
                }
                authorize.anyRequest().authenticated(); // 기타 모든 요청은 인증 필요
    }

}