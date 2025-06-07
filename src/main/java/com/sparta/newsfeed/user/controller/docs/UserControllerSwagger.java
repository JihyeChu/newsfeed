package com.sparta.newsfeed.user.controller.docs;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostSignupDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostSignupDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User", description = "회원가입, 로그인 인증 등 인증 관련 API를 제공합니다.")
@RequestMapping("/api/users")
public interface UserControllerSwagger {

    @Operation(summary = "회원가입", description = "회원가입을 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/signup")
    ResponseEntity<ResDTO<ResUserPostSignupDTO>> signup(@Valid @RequestBody ReqUserPostSignupDTO dto);

    @Operation(summary = "로그인", description = "로그인을 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "로그인 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/login")
    ResponseEntity<ResDTO<ResUserPostLoginDTO>> login(@Valid @RequestBody ReqUserPostLoginDTO dto);


}
