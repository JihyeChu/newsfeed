package com.sparta.newsfeed.user.controller.docs;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserDeleteAccountDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostSignupDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPatchProfileDTO;
import com.sparta.newsfeed.user.dto.res.ResUserGetProfileDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostSignupDTO;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원가입, 로그인 인증 등 인증 관련 API를 제공합니다.")
@RequestMapping("/api/users")
public interface UserControllerSwagger {

    @Operation(summary = "회원가입", description = "회원가입을 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/signup")
    ResponseEntity<ResDTO<ResUserPostSignupDTO>> signup(@Valid @RequestBody ReqUserPostSignupDTO dto);

    @Operation(summary = "로그인", description = "로그인을 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/login")
    ResponseEntity<ResDTO<ResUserPostLoginDTO>> login(@Valid @RequestBody ReqUserPostLoginDTO dto, HttpServletResponse response);

    @Operation(summary = "액세스 토큰 재발급", description = "토큰 재발급 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "토큰 재발급 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/token/refresh-token")
    ResponseEntity<ResDTO<Object>> createNewAccessToken(HttpServletRequest request, HttpServletResponse response);

        @Operation(summary = "프로필 조회", description = "프로필을 조회 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 조회 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "프로필 조회 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<ResDTO<ResUserGetProfileDTO>> getUserById(@PathVariable Long id);

    @Operation(summary = "프로필 수정", description = "프로필을 수정 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 수정 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "프로필 수정 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PatchMapping("/me")
    ResponseEntity<ResDTO<Object>> updateProfile(@Valid @RequestBody ReqUserPatchProfileDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "회원 탈퇴 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @DeleteMapping("/me")
    ResponseEntity<ResDTO<Object>> deleteAccount(@Valid @RequestBody ReqUserDeleteAccountDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails);
}
