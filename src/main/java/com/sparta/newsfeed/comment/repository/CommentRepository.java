package com.sparta.newsfeed.comment.repository;

import com.sparta.newsfeed.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByUserId(Long loginUserId);

}
