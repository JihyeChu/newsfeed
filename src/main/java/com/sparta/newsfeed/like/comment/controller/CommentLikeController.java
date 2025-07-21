package com.sparta.newsfeed.like.comment.controller;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.like.comment.controller.docs.CommentLikeControllerSwagger;
import com.sparta.newsfeed.like.comment.dto.res.ResCommentLikeCreateDTO;
import com.sparta.newsfeed.like.comment.service.CommentLikeService;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentLikeController implements CommentLikeControllerSwagger {

    public final CommentLikeService commentLikeService;

    @PostMapping("/{commentId}/likes")
    public ResponseEntity<ResDTO<ResCommentLikeCreateDTO>> createCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return new ResponseEntity<>(
                ResDTO.<ResCommentLikeCreateDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("좋아요를 생성했습니다.")
                        .data(commentLikeService.createCommentLike(commentId, userDetails.getUserEntity().getId()))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<ResDTO<Object>> deleteCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentLikeService.deleteCommentLike(commentId, userDetails.getUserEntity().getId());

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .code(HttpStatus.OK.value())
                        .message("좋아요를 취소했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}
