package com.sparta.newsfeed.like.post.entity;

import com.sparta.newsfeed.common.entity.BaseEntity;
import com.sparta.newsfeed.post.entity.PostEntity;
import com.sparta.newsfeed.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_likes")
public class PostLikeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public PostLikeEntity(PostEntity post, UserEntity user) {
        this.post = post;
        this.user = user;
    }
}
