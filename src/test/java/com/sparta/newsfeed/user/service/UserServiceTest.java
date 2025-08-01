package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.common.utils.JwtUtil;
import com.sparta.newsfeed.user.dto.req.ReqUserPostLoginDTO;
import com.sparta.newsfeed.user.entity.RefreshToken;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.entity.UserRole;
import com.sparta.newsfeed.user.repository.RefreshTokenRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletResponse response;

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
        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.findByNicknameAndDeletedAtNull(nickname)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("rawPassword", "encodePassword")).thenReturn(true);
        when(jwtUtil.generateAccessToken(userId, nickname)).thenReturn(accessToken);
        when(jwtUtil.generateRefreshToken(userId)).thenReturn(refreshToken);
        when(refreshTokenRepository.findByIdAndNickname(userId, nickname)).thenReturn(Optional.empty());

        Cookie mockRefreshCookie = new Cookie("Refresh-jwt", refreshToken);
        when(jwtUtil.generateRefreshJwtCookie(refreshToken)).thenReturn(mockRefreshCookie);

        HttpServletResponse response = mock(HttpServletResponse.class);

        // when
        userService.login(loginDto, response);

        // then
        verify(userRepository).findByNicknameAndDeletedAtNull(nickname);
        verify(passwordEncoder).matches("rawPassword", "encodePassword");
        verify(jwtUtil).generateAccessToken(userId, nickname);
        verify(jwtUtil).generateRefreshToken(userId);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
        verify(response).setHeader("Authorization", "Bearer " + accessToken);
        verify(response).addCookie(mockRefreshCookie);
    }


    // 존재하지 않는 사용자일 경우 예외가 잘 발생하는가?
    @Test
    void 존재하지_않는_사용자() {
        // given
        when(userRepository.findByNicknameAndDeletedAtNull(nickname)).thenReturn(Optional.empty());

        // when, then
        BusinessException exception = assertThrows(BusinessException.class, () -> userService.login(loginDto, response));

        assertEquals(ErrorCode.NOT_FOUND_USER, exception.getErrorCode(), exception.getMessage());
    }

    // 비밀번호가 일치하지 않을 경우 예외가 발생하는가?
    @Test
    void 비밀번호_불일치() {
        // given
        when(userRepository.findByNicknameAndDeletedAtNull(nickname)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("rawPassword", "encodePassword")).thenReturn(false);

        // when, then
        BusinessException exception = assertThrows(BusinessException.class, () -> userService.login(loginDto, response));

        assertEquals(ErrorCode.INVALID_CURRENT_PASSWORD, exception.getErrorCode(), exception.getMessage());
    }
}