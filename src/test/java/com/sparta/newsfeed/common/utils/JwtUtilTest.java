package com.sparta.newsfeed.common.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

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
        String token = jwtUtil.generateToken(nickname);

        // then
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    // 토큰에서 nickname 추출이 가능한가?
    @Test
    void 닉네임_추출() {
        // given
        String token = jwtUtil.generateToken(nickname);

        // when
        String extracted = jwtUtil.extractNickname(token);

        // then
        assertEquals(nickname, extracted);
    }

    // 토큰이 유효한가?
    @Test
    void 토큰_유효성_검증() {
        // given
        String token = jwtUtil.generateToken(nickname);

        // when
        boolean isValid = jwtUtil.validateToken(token);

        // then
        assertTrue(isValid);
    }
}