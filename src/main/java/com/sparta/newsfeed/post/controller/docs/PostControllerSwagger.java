package com.sparta.newsfeed.post.controller.docs;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.post.dto.req.ReqPostCreateDTO;
import com.sparta.newsfeed.post.dto.req.ReqPostPatchDTO;
import com.sparta.newsfeed.post.dto.res.ResPostListDTO;
import com.sparta.newsfeed.post.res.ResPostCreateDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 생성, 조회, 수정, 삭제 관련 API를 제공합니다.")
@RequestMapping("/api/posts")
public interface PostControllerSwagger {

    @Operation(summary = "게시글 생성", description = "게시글을 생성 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "게시글 생성 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping
    ResponseEntity<ResDTO<ResPostCreateDTO>> createPost(@Valid @RequestBody ReqPostCreateDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "게시글 조회", description = "게시글을 조회 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "게시글 조회 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @GetMapping
    ResponseEntity<ResDTO<List<ResPostListDTO>>> getPostList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "게시글 수정", description = "게시글을 수정 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "게시글 수정 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PatchMapping("/{id}")
    ResponseEntity<ResDTO<Object>> updatePost(@Valid @RequestBody ReqPostPatchDTO dto, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "게시글 삭제 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<ResDTO<Object>> deletePost(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails);
}