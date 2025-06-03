package com.sparta.newsfeed.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

}
