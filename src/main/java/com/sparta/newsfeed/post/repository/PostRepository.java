package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("select p from PostEntity p " +
            "join fetch p.user u " +
            "where p.deletedAt is null")
    Page<PostEntity> findAllWithUser(Pageable pageable);

    @Query("select p from PostEntity p " +
            "join fetch p.user u " +
            "where p.deletedAt is null")
    List<PostEntity> findAllWithUser();
}
