package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
