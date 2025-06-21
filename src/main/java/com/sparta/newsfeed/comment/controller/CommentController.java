package com.sparta.newsfeed.comment.controller;

import com.sparta.newsfeed.comment.dto.req.ReqCommentCreateDTO;
import com.sparta.newsfeed.comment.dto.res.ResCommentCreateDTO;
import com.sparta.newsfeed.comment.dto.res.ResCommentListDTO;
import com.sparta.newsfeed.comment.service.CommentService;
import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ResDTO<ResCommentCreateDTO>> createComment(@PathVariable Long postId,
                                                                     @Valid @RequestBody ReqCommentCreateDTO dto,
                                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        return new ResponseEntity<>(
                ResDTO.<ResCommentCreateDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("댓글을 생성했습니다.")
                        .data(commentService.createComment(postId, dto, userDetails.getUserEntity().getId()))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/users/me/comments")
    public ResponseEntity<ResDTO<List<ResCommentListDTO>>> getCommentList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return new ResponseEntity<>(
                ResDTO.<List<ResCommentListDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .message("회원의 댓글을 전체 조회했습니다.")
                        .data(commentService.getCommentList(userDetails.getUserEntity().getId()))
                        .build(),
                HttpStatus.CREATED
        );
    }
}
