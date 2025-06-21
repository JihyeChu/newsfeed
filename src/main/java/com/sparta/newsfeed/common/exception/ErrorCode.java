package com.sparta.newsfeed.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // USER 도메인 (40001~40019)
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, 40901, "이미 사용중인 아이디 입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 40401, "아이디가 존재하지 않습니다."),
    INVALID_CURRENT_PASSWORD(HttpStatus.UNAUTHORIZED, 40101, "현재 비밀번호가 일치하지 않습니다."),
    REQUIRED_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST, 40001, "현재 비밀번호를 입력해 주세요."),
    SAME_AS_OLD_PASSWORD(HttpStatus.BAD_REQUEST, 40002, "기존 비밀번호와 동일합니다."),
    FORBIDDEN_USER_UPDATE(HttpStatus.FORBIDDEN, 40301, "다른 사용자의 프로필은 수정할 수 없습니다."),

    // POST 도메인 (40020~40039)
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, 40420, "게시글이 존재하지 않습니다."),
    FORBIDDEN_POST_UPDATE(HttpStatus.FORBIDDEN, 40320, "다른 사용자의 게시글은 수정할 수 없습니다."),
    FORBIDDEN_POST_DELETE(HttpStatus.FORBIDDEN, 40321, "다른 사용자의 게시글은 삭제할 수 없습니다."),

    // FOLLOW 도메인 (40040~40049)
    NOT_FOUND_FOLLOW(HttpStatus.NOT_FOUND, 40440, "팔로우가 존재하지 않습니다."),
    FORBIDDEN_FOLLOW_DELETE(HttpStatus.FORBIDDEN, 40340, "다른 사용자의 팔로우를 삭제할 수 없습니다."),

    // COMMENT 도메인 (40050~40059)
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, 40450, "댓글이 존재하지 않습니다."),
    FORBIDDEN_COMMENT_UPDATE(HttpStatus.FORBIDDEN, 40350, "다른 사용자의 댓글은 수정할 수 없습니다."),
    INVALID_COMMENT_TO_POST(HttpStatus.BAD_REQUEST, 40050, "해당 게시글에 등록된 댓글이 아닙니다."),
    FORBIDDEN_COMMENT_DELETE(HttpStatus.FORBIDDEN, 40351, "다른 사용자의 댓글은 삭제할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

}
