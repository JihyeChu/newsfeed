package com.sparta.newsfeed.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtUtilTest {

    private JwtUtil jwtUtil;

    private final String SECRET_KEY = Base64.getEncoder().encodeToString("mytestsecretKey1234567890mytestsecretKey1234567890".getBytes());

    @BeforeAll
    void setUp() {
        jwtUtil = new JwtUtil(null);
        ReflectionTestUtils.setField(jwtUtil, "secretKey", SECRET_KEY);
        jwtUtil.init();
    }

    private final String nickname = "testUser";

    // 토큰이 잘 생성 되는가?
    @Test
    void 토큰_생성_성공() {
        // when
        Long userId = 1L;
        String token = jwtUtil.generateAccessToken(userId, nickname);

        // then
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    // 토큰에서 nickname 추출이 가능한가?
    @Test
    void 닉네임_추출() {
        // given
        Long userId = 1L;
        String token = jwtUtil.generateAccessToken(userId, nickname);

        // when
        String extracted = jwtUtil.extractNickname(token);

        // then
        assertEquals(nickname, extracted);
    }

    // 토큰이 유효한가?
    @Test
    void 토큰_유효성_검증() {
        // given
        Long userId = 1L;
        String token = jwtUtil.generateAccessToken(userId, nickname);

        // when
        boolean isValid = jwtUtil.validateToken(token);

        // then
        assertTrue(isValid);
    }

    // 위조된 토큰은 실패하는가?
    @Test
    void 위조된_토큰_검증_실패() {
        // given
        String invalidToken = "이건 위조된 토큰 입니다.";

        // when
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // then
        assertFalse(isValid);
    }

    @Test
    void 만료된_토큰_검증() {
        // given
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() - 1000); // 이미 만료

        Key key = (Key) ReflectionTestUtils.getField(jwtUtil, "key");

        String expiredToken = Jwts.builder()
                .setSubject(nickname)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // when
        boolean inValid = jwtUtil.validateToken(expiredToken);

        // then
        assertFalse(inValid);
    }
}