package com.sparta.newsfeed.like.comment.repository;

import com.sparta.newsfeed.comment.entity.CommentEntity;
import com.sparta.newsfeed.like.comment.entity.CommentLikeEntity;
import com.sparta.newsfeed.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    Optional<CommentLikeEntity> findByCommentAndUser(CommentEntity commentEntity, UserEntity userEntity);
}
