package com.bbf.bebefriends.global.config;

import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.service.UserService;
import com.bbf.bebefriends.member.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    public CustomAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = resolveToken(request);

        if (accessToken != null && JwtTokenUtil.validateAccessToken(accessToken)) {
            String uid = JwtTokenUtil.getUserIdFromToken(accessToken);

            User user = userService.findByUid(Long.valueOf(uid));

            UserDetailsImpl userDetails = new UserDetailsImpl(user);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
            String deviceType = request.getHeader("Device-Type");

            if ("PC".equalsIgnoreCase(deviceType)) {
                // PC 에서 유저가 아닐때 "guest" 역할 부여
                UserDetailsImpl userDetails = new UserDetailsImpl(User.createGuestUser());
                UsernamePasswordAuthenticationToken guestAuthentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(guestAuthentication);
            }
        }

        // 다음 필터 계속 실행
        filterChain.doFilter(request, response);
    }

    // 토큰을 HTTP 헤더에서 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}