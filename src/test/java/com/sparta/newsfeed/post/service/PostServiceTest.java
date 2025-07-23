package com.sparta.newsfeed.post.service;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.dto.req.ReqPostCreateDTO;
import com.sparta.newsfeed.post.dto.req.ReqPostPatchDTO;
import com.sparta.newsfeed.post.dto.res.ResPostCreateDTO;
import com.sparta.newsfeed.post.entity.PostEntity;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.entity.UserRole;
import com.sparta.newsfeed.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private UserEntity user;
    private PostEntity post;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .nickname("username")
                .password("encodePassword")
                .role(UserRole.USER)
                .username("username")
                .email("test@email.com")
                .build();

        post = PostEntity.create("제목1", "내용1", user);
        ReflectionTestUtils.setField(post, "id", 1L);
    }

    @Test
    void 게시글_생성() {
        // given
        ReqPostCreateDTO reqDto = new ReqPostCreateDTO("제목1", "내용1");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        // when
        ResPostCreateDTO result = postService.createPost(reqDto, 1L);

        // then
        assertEquals(post.getTitle(), result.getTitle());
        assertEquals(post.getContent(), result.getContent());
    }

    @Test
    void 게시글_수정시_본인이_아닌경우() {
        // given
        ReqPostPatchDTO reqDto = new ReqPostPatchDTO("제목_수정", null);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        Long otherUserId = 2L;

        // when, then
        BusinessException exception = assertThrows(BusinessException.class, () -> postService.updatePost(reqDto, 1L, otherUserId));
        assertEquals(ErrorCode.FORBIDDEN_POST_UPDATE, exception.getErrorCode(), exception.getMessage());
    }

    @Test
    void 수정시_게시글이_존재하지_않는경우() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        ReqPostPatchDTO reqDto = new ReqPostPatchDTO("제목", null);

        // when, then
        assertThrows(BusinessException.class, () -> postService.updatePost(reqDto, 1L, 1L));
    }

    @Test
    void 게시글_삭제시_본인이_아닌경우() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        Long otherUserId = 2L;

        // when, then
        BusinessException exception = assertThrows(BusinessException.class, () -> postService.deletePost(1L, otherUserId));
        assertEquals(ErrorCode.FORBIDDEN_POST_DELETE, exception.getErrorCode(), exception.getMessage());
    }
}