package com.sparta.newsfeed.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, 40901, "이미 사용중인 아이디 입니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

}
