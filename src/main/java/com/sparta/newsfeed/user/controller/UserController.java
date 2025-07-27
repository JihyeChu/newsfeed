package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.user.controller.docs.UserControllerSwagger;
import com.sparta.newsfeed.user.dto.req.ReqUserDeleteAccountDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPatchProfileDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostSignupDTO;
import com.sparta.newsfeed.user.dto.res.ResUserGetProfileDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostSignupDTO;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<ResDTO<Object>> login(@Valid @RequestBody ReqUserPostLoginDTO dto, HttpServletResponse response) {
        userService.login(dto, response);

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .code(HttpStatus.OK.value())
                        .message("로그인 되었습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/token/refresh-token")
    public ResponseEntity<ResDTO<Object>> createNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        userService.createNewAccessToken(request, response);

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .code(HttpStatus.OK.value())
                        .message("새로운 Access Token 발급에 성공하였습니다.")
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

    @PatchMapping("/me")
    public ResponseEntity<ResDTO<Object>> updateProfile(@Valid @RequestBody ReqUserPatchProfileDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails) {

        userService.updateProfile(dto, userDetails.getUserEntity().getId());

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .code(HttpStatus.OK.value())
                        .message("프로필 수정에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<ResDTO<Object>> deleteAccount(@Valid @RequestBody ReqUserDeleteAccountDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails) {

        userService.deleteAccount(dto, userDetails.getUserEntity().getId());

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .code(HttpStatus.OK.value())
                        .message("탈퇴에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}
