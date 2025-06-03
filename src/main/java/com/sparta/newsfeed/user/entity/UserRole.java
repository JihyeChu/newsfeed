package com.sparta.newsfeed.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private final String authority;

    public static class Authority {
        public static final String USER = "회원";
        public static final String ADMIN = "관리자";
    }
}
