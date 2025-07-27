package com.sparta.newsfeed.common.filter;

import com.sparta.newsfeed.common.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JwtFilter")
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String jwt = null;

        String authorizationHeader = request.getHeader("Authorization");

        if (requestURI.equals("/api/users/signup") || requestURI.equals("/api/users/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.info("JWT 토큰이 필요 합니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 필요 합니다.");
            return;
        }

        jwt = authorizationHeader.substring(7);

        if (jwtUtil.validateToken(jwt)) {
            // 토큰으로부터 인증 정보(Authentication) 객체 생성
            Authentication authentication = jwtUtil.getAuthentication(jwt);

            // SecurityContext에 인증 정보를 저장해, 이후 인증된 사용자로 인식되도록 함
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
