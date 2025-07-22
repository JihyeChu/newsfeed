package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.common.utils.JwtUtil;
import com.sparta.newsfeed.user.dto.req.ReqUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostLoginDTO;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.entity.UserRole;
import com.sparta.newsfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    private final String nickname = "testUser";

    private final UserEntity userEntity = UserEntity.builder()
            .nickname(nickname)
            .password("encodePassword")
            .role(UserRole.USER)
            .username("username")
            .email("test@email.com")
            .build();

    private final ReqUserPostLoginDTO loginDto = new ReqUserPostLoginDTO(nickname, "rawPassword");

    // 유저가 존재할 때, 올바른 비밀번호이면 토큰 발급이 정상적으로 이루어지는가?
    @Test
    void 로그인_성공시_토큰_발급() {
        // given
        when(userRepository.findByNicknameAndDeletedAtNull(nickname)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("rawPassword", "encodePassword")).thenReturn(true);
        when(jwtUtil.generateToken(nickname)).thenReturn("mock-jwt-token");

        // when
        ResUserPostLoginDTO result = userService.login(loginDto);

        // then
        assertNotNull(result);
        assertEquals("mock-jwt-token", result.getToken());
        verify(userRepository).findByNicknameAndDeletedAtNull(nickname);
        verify(passwordEncoder).matches("rawPassword", "encodePassword");
        verify(jwtUtil).generateToken(nickname);
    }
}