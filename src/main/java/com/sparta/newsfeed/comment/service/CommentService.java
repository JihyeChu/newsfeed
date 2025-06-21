package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.comment.dto.req.ReqCommentCreateDTO;
import com.sparta.newsfeed.comment.dto.req.ReqCommentUpdateDTO;
import com.sparta.newsfeed.comment.dto.res.ResCommentCreateDTO;
import com.sparta.newsfeed.comment.dto.res.ResCommentListDTO;
import com.sparta.newsfeed.comment.entity.CommentEntity;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.entity.PostEntity;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResCommentCreateDTO createComment(Long postId, ReqCommentCreateDTO dto, Long loginUserId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_POST)
        );

        UserEntity userEntity = userRepository.findById(loginUserId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        CommentEntity commentEntityForSaving = CommentEntity.create(dto.getContent(), postEntity, userEntity);

        return ResCommentCreateDTO.of(commentRepository.save(commentEntityForSaving));
    }

    @Transactional(readOnly = true)
    public List<ResCommentListDTO> getCommentList(Long loginUserId) {
        List<CommentEntity> commentEntityList = commentRepository.findAllByUserId(loginUserId);

        return commentEntityList.stream()
                .map(ResCommentListDTO::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, ReqCommentUpdateDTO dto, Long loginUserId) {
        CommentEntity commentEntityForUpdate = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT)
        );

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_POST)
        );

        if (!commentEntityForUpdate.getPost().getId().equals(postEntity.getId())) {
            throw new BusinessException(ErrorCode.INVALID_COMMENT_TO_POST);
        }

        UserEntity postAuthor = commentEntityForUpdate.getPost().getUser();

        if (!commentEntityForUpdate.getUser().getId().equals(loginUserId) && !postAuthor.getId().equals(loginUserId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_COMMENT_UPDATE);
        }

        if (dto.getContent() != null) {
            commentEntityForUpdate.update(dto.getContent());
        }
    }
}
