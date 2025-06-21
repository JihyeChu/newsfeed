package com.sparta.newsfeed.comment.controller.docs;

import com.sparta.newsfeed.comment.dto.req.ReqCommentCreateDTO;
import com.sparta.newsfeed.comment.dto.req.ReqCommentUpdateDTO;
import com.sparta.newsfeed.comment.dto.res.ResCommentCreateDTO;
import com.sparta.newsfeed.comment.dto.res.ResCommentListDTO;
import com.sparta.newsfeed.common.dto.ResDTO;
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

@Tag(name = "Comment", description = "댓글 생성, 조회, 수정, 삭제 관련 API를 제공합니다.")
@RequestMapping("/api")
public interface CommentControllerSwagger {

    @Operation(summary = "댓글 생성", description = "댓글을 생성 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 생성 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "댓글 생성 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/posts/{postId}/comments")
    ResponseEntity<ResDTO<ResCommentCreateDTO>> createComment(@PathVariable Long postId,
                                                              @Valid @RequestBody ReqCommentCreateDTO dto,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "댓글 조회", description = "댓글을 조회 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "댓글 조회 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @GetMapping("/users/me/comments")
    ResponseEntity<ResDTO<List<ResCommentListDTO>>> getCommentList(@AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "댓글 수정", description = "댓글을 수정 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "댓글 수정 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<ResDTO<Object>> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                 @Valid @RequestBody ReqCommentUpdateDTO dto,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "댓글 삭제 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<ResDTO<Object>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails);
}
