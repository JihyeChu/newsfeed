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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
