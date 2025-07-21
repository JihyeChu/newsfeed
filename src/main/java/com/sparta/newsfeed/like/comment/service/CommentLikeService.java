package com.sparta.newsfeed.like.comment.service;

import com.sparta.newsfeed.comment.entity.CommentEntity;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.like.comment.dto.res.ResCommentLikeCreateDTO;
import com.sparta.newsfeed.like.comment.entity.CommentLikeEntity;
import com.sparta.newsfeed.like.comment.repository.CommentLikeRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    public final UserRepository userRepository;
    public final CommentRepository commentRepository;
    public final CommentLikeRepository commentLikeRepository;

    @Transactional
    public ResCommentLikeCreateDTO createCommentLike(Long commentId, Long loginUserId) {
        // 사용자 존재 여부
        UserEntity userEntity = userRepository.findById(loginUserId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        // 댓글 존재 여부
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT)
        );

        // 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없음
        if (commentEntity.getUser().getId().equals(loginUserId)) {
            throw new BusinessException(ErrorCode.CANNOT_LIKE_OWN_COMMENT);
        }

        Optional<CommentLikeEntity> commentLikeEntity = commentLikeRepository.findByCommentAndUser(commentEntity, userEntity);

        if (commentLikeEntity.isEmpty()) {
            CommentLikeEntity commentLikeEntityForSaving = CommentLikeEntity.create(commentEntity, userEntity);
            return ResCommentLikeCreateDTO.of(commentLikeRepository.save(commentLikeEntityForSaving));
        }

        return ResCommentLikeCreateDTO.of(commentLikeEntity.get());
    }

    @Transactional
    public void deleteCommentLike(Long commentId, Long loginUserId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT)
        );

        UserEntity userEntity = userRepository.findById(loginUserId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        Optional<CommentLikeEntity> commentLikeEntity = commentLikeRepository.findByCommentAndUser(commentEntity, userEntity);

        commentLikeEntity.ifPresent(commentLikeRepository::delete);
    }
}
