package com.sparta.newsfeed.post.controller;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.post.controller.docs.PostControllerSwagger;
import com.sparta.newsfeed.post.dto.req.ReqPostCreateDTO;
import com.sparta.newsfeed.post.res.ResPostCreateDTO;
import com.sparta.newsfeed.post.service.PostService;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController implements PostControllerSwagger {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ResDTO<ResPostCreateDTO>> createPost(@Valid @RequestBody ReqPostCreateDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return new ResponseEntity<>(
                ResDTO.<ResPostCreateDTO>builder()
                        .message("게시글 생성에 성공하였습니다.")
                        .code(HttpStatus.CREATED.value())
                        .data(postService.createPost(dto, userDetails.getUserEntity().getId()))
                        .build(),
                HttpStatus.CREATED
        );
    }
}
