package com.sparta.newsfeed.like.post.controller;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.like.post.controller.docs.PostLikeControllerSwagger;
import com.sparta.newsfeed.like.post.dto.res.ResPostLikeCreateDTO;
import com.sparta.newsfeed.like.post.service.PostLikeService;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostLikeController implements PostLikeControllerSwagger {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/likes")
    public ResponseEntity<ResDTO<ResPostLikeCreateDTO>> createPostLike(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return new ResponseEntity<>(
                ResDTO.<ResPostLikeCreateDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("좋아요를 생성했습니다.")
                        .data(postLikeService.createPostLike(postId, userDetails.getUserEntity().getId()))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ResDTO<Object>> deletePostLike(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postLikeService.deletePostLike(postId, userDetails.getUserEntity().getId());

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .code(HttpStatus.OK.value())
                        .message("좋아요를 취소했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}
