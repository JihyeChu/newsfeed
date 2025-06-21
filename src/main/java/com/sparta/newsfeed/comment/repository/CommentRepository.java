package com.sparta.newsfeed.comment.repository;

import com.sparta.newsfeed.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
