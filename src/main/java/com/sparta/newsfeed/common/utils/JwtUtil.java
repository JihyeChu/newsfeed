package com.sparta.newsfeed.common.utils;

import com.sparta.newsfeed.user.security.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    private static final String REFRESH_HEADER = "Refresh-jwt";
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L * 24 * 7; // 7일
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L * 24; // 24시간
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${service.jwt.secret-key}")
    private String secretKey;
    private Key key;

    public JwtUtil(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    // 애플리케이션 시작 시 Key 객체 초기화
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT Access Token 생성
    public String generateAccessToken(Long userId, String nickname) {
        Date date = new Date();

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("nickname", nickname)
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // JWT Refresh Token 생성
    public String generateRefreshToken(Long userId) {
        Date date = new Date();

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();

    }

    public Cookie generateRefreshJwtCookie(String refreshToken) {
        Cookie refreshJwtCookie = new Cookie(REFRESH_HEADER, refreshToken);

        refreshJwtCookie.setHttpOnly(true);
        refreshJwtCookie.setSecure(true);
        refreshJwtCookie.setPath("/");
        refreshJwtCookie.setMaxAge((int) REFRESH_TOKEN_EXPIRE_TIME);

        return refreshJwtCookie;
    }

    // 토큰에서 userId 추출
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 토큰에서 아이디(닉네임) 추출
    public String extractNickname(String token) {
        return extractAllClaims(token).get("nickname", String.class);
    }

    // 토큰에서 Claims 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.", e);
        }
        return false;
    }

    // 인증 객체 생성
    public Authentication getAuthentication(String token) {
        String nickname = extractNickname(token);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(nickname);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
