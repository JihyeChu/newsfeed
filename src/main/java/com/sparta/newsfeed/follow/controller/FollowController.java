package com.sparta.newsfeed.follow.controller;

import com.sparta.newsfeed.common.dto.ResDTO;
import com.sparta.newsfeed.follow.dto.req.ReqFollowCreateDTO;
import com.sparta.newsfeed.post.dto.res.ResPostCreateDTO;
import com.sparta.newsfeed.follow.service.FollowService;
import com.sparta.newsfeed.user.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<ResDTO<Object>> createFollow(@Valid @RequestBody ReqFollowCreateDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        followService.createFollow(dto, userDetails.getUserEntity().getId());

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .message("사용자를 팔로우했습니다.")
                        .code(HttpStatus.CREATED.value())
                        .build(),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> unFollow(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        followService.unFollow(id, userDetails.getUserEntity().getId());

        return new ResponseEntity<>(
                ResDTO.<Object>builder()
                        .message("사용자를 언팔로우했습니다.")
                        .code(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        );
    }
}
