package com.sparta.newsfeed.common.exception;

import com.sparta.newsfeed.common.dto.ResDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ResDTO<Object>> exceptionHandler(BusinessException ex) {
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(ex.getErrorCode().getCode())
                        .message(ex.getMessage())
                        .build(),
                ex.getErrorCode().getHttpStatus()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String firstMessage = null;
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if (firstMessage == null) {
                firstMessage = error.getDefaultMessage(); // 첫 번째 에러 메시지 저장
            }
        }

        ResDTO<Map<String, String>> response = ResDTO.<Map<String, String>>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(firstMessage)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
