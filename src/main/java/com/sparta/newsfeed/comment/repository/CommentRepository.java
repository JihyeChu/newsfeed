package com.sparta.newsfeed.comment.repository;

import com.sparta.newsfeed.comment.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByUserId(Long loginUserId);

    @Query("select c from CommentEntity c " +
            "join fetch c.user u " +
            "where c.deletedAt is null")
    Page<CommentEntity> findAllWithUser(Pageable pageable);
}
