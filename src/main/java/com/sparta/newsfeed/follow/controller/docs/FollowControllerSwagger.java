package com.sparta.newsfeed.follow.controller.docs;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.follow.dto.req.ReqFollowCreateDTO;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Follow", description = "팔로우 생성, 삭제 관련 API를 제공합니다.")
@RequestMapping("/api/follows")
public interface FollowControllerSwagger {

    @Operation(summary = "팔로우 생성", description = "팔로우를 생성 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "팔로우 생성 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "팔로우 생성 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping
    ResponseEntity<ResDTO<Object>> createFollow(@Valid @RequestBody ReqFollowCreateDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails);
}
