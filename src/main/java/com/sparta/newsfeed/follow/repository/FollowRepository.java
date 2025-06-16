package com.sparta.newsfeed.follow.repository;

import com.sparta.newsfeed.follow.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
}
