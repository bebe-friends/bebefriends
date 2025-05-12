package com.bbf.bebefriends.member.util;

import com.bbf.bebefriends.member.dto.JwtPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtTokenUtil {

    private static final Key ACCESS_TOKEN_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 상수로 키 생성

    /**
     * Access Token을 생성합니다.
     *
     * @param userId 사용자의 고유 식별자
     * @param payload 토큰에 추가할 커스텀 클레임
     * @return 생성된 Access Token (JWT)
     */
    public static String createAccessToken(String userId, JwtPayload payload) {
        // JwtPayload 객체를 Map 형식으로 변환
        Map<String, Object> claims = Map.of(
                "role", payload.getRole(),
                "email", payload.getEmail()
        );

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .signWith(ACCESS_TOKEN_SECRET_KEY)
                .compact();
    }

    /**
     * Access Token의 유효성을 검증합니다.
     *
     * @param token 검증할 Access Token
     * @return 유효하면 true, 그렇지 않으면 false
     */
    public static boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


    /**
     * 주어진 Access Token에서 사용자 정의 데이터(role, email)를 추출합니다.
     *
     * @param token 데이터를 추출할 Access Token
     * @return 추출된 role과 email 정보를 포함하는 {@link JwtPayload} 객체
     */
    public static JwtPayload extractPayload(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String role = claims.get("role", String.class);
        String email = claims.get("email", String.class);

        return new JwtPayload(role, email);
    }

    /**
     * Access Token에서 사용자 ID를 추출합니다.
     *
     * @param token 사용자 ID를 추출할 Refresh Token
     * @return Refresh Token에 포함된 사용자 ID
     */
    public static String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET_KEY)
                .build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}