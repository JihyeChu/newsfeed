package com.sparta.newsfeed.post.controller;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.post.controller.docs.PostControllerSwagger;
import com.sparta.newsfeed.post.dto.req.ReqPostCreateDTO;
import com.sparta.newsfeed.post.dto.req.ReqPostPatchDTO;
import com.sparta.newsfeed.post.dto.res.ResPostListDTO;
import com.sparta.newsfeed.post.res.ResPostCreateDTO;
import com.sparta.newsfeed.post.service.PostService;
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

    @GetMapping
    public ResponseEntity<ResDTO<List<ResPostListDTO>>> getPostList(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(
                ResDTO.<List<ResPostListDTO>>builder()
                        .message("게시글 조회에 성공하였습니다.")
                        .code(HttpStatus.OK.value())
                        .data(postService.getPostList(page, size))
                        .build(),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> updatePost(@Valid @RequestBody ReqPostPatchDTO dto, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.updatePost(dto, id, userDetails.getUserEntity().getId());

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .message("게시글 수정에 성공하였습니다.")
                        .code(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deletePost(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(id, userDetails.getUserEntity().getId());

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .message("게시글 삭제에 성공하였습니다.")
                        .code(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        );
    }


}
