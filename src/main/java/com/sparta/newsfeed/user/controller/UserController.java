package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.user.controller.docs.UserControllerSwagger;
import com.sparta.newsfeed.user.dto.res.ResUserPostSignupDTO;
import com.sparta.newsfeed.user.service.UserService;
import com.sparta.newsfeed.user.dto.req.ReqUserPostSignupDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserControllerSwagger {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResDTO<ResUserPostSignupDTO>> signup(@Valid @RequestBody ReqUserPostSignupDTO dto) {

        return new ResponseEntity<>(
                ResDTO.<ResUserPostSignupDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("회원가입이 완료되었습니다.")
                        .data(userService.signup(dto))
                        .build(),
                HttpStatus.CREATED
        );
    }

}
