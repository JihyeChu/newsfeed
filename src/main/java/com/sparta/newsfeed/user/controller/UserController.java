package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.user.controller.docs.UserControllerSwagger;
import com.sparta.newsfeed.user.dto.req.ReqUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostSignupDTO;
import com.sparta.newsfeed.user.dto.req.ResUserPatchProfileDTO;
import com.sparta.newsfeed.user.dto.res.ResUserGetProfileDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostSignupDTO;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<ResDTO<ResUserPostLoginDTO>> login(@Valid @RequestBody ReqUserPostLoginDTO dto) {

        return new ResponseEntity<>(
                ResDTO.<ResUserPostLoginDTO>builder()
                        .code(HttpStatus.OK.value())
                        .message("로그인 되었습니다.")
                        .data(userService.login(dto))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResUserGetProfileDTO>> getUserById(@PathVariable Long id) {

        return new ResponseEntity<>(
                ResDTO.<ResUserGetProfileDTO>builder()
                        .code(HttpStatus.OK.value())
                        .message("프로필 조회에 성공하였습니다.")
                        .data(userService.getUserById(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> updateProfile(@PathVariable Long id, @Valid @RequestBody ResUserPatchProfileDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails) {

        userService.updateProfile(id, dto, userDetails.getUserEntity().getId());

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .code(HttpStatus.OK.value())
                        .message("프로필 수정에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}
