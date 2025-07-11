package com.sparta.newsfeed.like.post.repository;

import com.sparta.newsfeed.like.post.entity.PostLikeEntity;
import com.sparta.newsfeed.post.entity.PostEntity;
import com.sparta.newsfeed.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {

    boolean existsByPostAndUser(PostEntity postEntity, UserEntity userEntity);

}
