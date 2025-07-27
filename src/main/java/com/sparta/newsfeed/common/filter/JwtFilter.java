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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JwtFilter")
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/api/users/signup") || requestURI.equals("/api/users/login") || requestURI.equals("/api/users/token/refresh-token")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = checkAccessToken(request);

        // Access Token 이 유효할 경우
        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            setAuthentication(accessToken);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 유효하지 않거나, 만료되었습니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String checkAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header)) {
            return header;
        }
        return null;
    }
}
