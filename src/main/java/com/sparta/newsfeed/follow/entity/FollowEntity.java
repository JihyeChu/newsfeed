package com.sparta.newsfeed.follow.entity;

import com.sparta.newsfeed.common.entity.BaseEntity;
import com.sparta.newsfeed.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "follows")
public class FollowEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private UserEntity follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id")
    private UserEntity followee;

    @Builder
    public FollowEntity(UserEntity follower, UserEntity followee) {
        this.follower = follower;
        this.followee = followee;
    }
}
