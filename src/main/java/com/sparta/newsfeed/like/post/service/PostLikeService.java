package com.sparta.newsfeed.like.post.service;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.like.post.dto.res.ResPostLikeCreateDTO;
import com.sparta.newsfeed.like.post.entity.PostLikeEntity;
import com.sparta.newsfeed.like.post.repository.PostLikeRepository;
import com.sparta.newsfeed.post.entity.PostEntity;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResPostLikeCreateDTO createPostLike(Long postId, Long loginUserId) {
        // 사용자 존재 여부
        UserEntity userEntity = userRepository.findById(loginUserId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        // 게시글 존재 여부
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_POST)
        );

        // 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없음
        if (postEntity.getUser().getId().equals(loginUserId)) {
            throw new BusinessException(ErrorCode.CANNOT_LIKE_OWN_POST);
        }

        Optional<PostLikeEntity> postLikeEntity = postLikeRepository.findByPostAndUser(postEntity, userEntity);

        if (postLikeEntity.isEmpty()) {
            PostLikeEntity postLikeEntityForSaving = PostLikeEntity.create(postEntity, userEntity);
            return ResPostLikeCreateDTO.of(postLikeRepository.save(postLikeEntityForSaving));
        }

        return ResPostLikeCreateDTO.of(postLikeEntity.get());
    }

    @Transactional
    public void deletePostLike(Long postId, Long loginUserId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_POST)
        );

        UserEntity userEntity = userRepository.findById(loginUserId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        Optional<PostLikeEntity> postLikeEntity = postLikeRepository.findByPostAndUser(postEntity, userEntity);

        postLikeEntity.ifPresent(postLikeRepository::delete);
    }
}
