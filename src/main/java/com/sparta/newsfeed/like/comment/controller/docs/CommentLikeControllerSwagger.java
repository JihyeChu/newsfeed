package com.sparta.newsfeed.like.comment.controller.docs;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.like.comment.dto.res.ResCommentLikeCreateDTO;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "CommentLike", description = "댓글 좋아요 생성, 삭제 관련 API를 제공합니다.")
@RequestMapping("/api/comments")
public interface CommentLikeControllerSwagger {

    @Operation(summary = "댓글 좋아요 생성", description = "댓글 좋아요를 생성 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 좋아요 생성 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "댓글 좋아요 생성 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/{commentId}/likes")
    ResponseEntity<ResDTO<ResCommentLikeCreateDTO>> createCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "댓글 좋아요 취소", description = "댓글 좋아요를 취소 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 좋아요 취소 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "댓글 좋아요 취소 실패", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @DeleteMapping("/{commentId}/likes")
    ResponseEntity<ResDTO<Object>> deleteCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails);
}
