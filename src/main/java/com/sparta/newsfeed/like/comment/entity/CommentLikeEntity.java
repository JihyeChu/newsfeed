package com.sparta.newsfeed.like.comment.entity;

import com.sparta.newsfeed.comment.entity.CommentEntity;
import com.sparta.newsfeed.common.entity.BaseEntity;
import com.sparta.newsfeed.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment_likes")
public class CommentLikeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public CommentLikeEntity(CommentEntity comment, UserEntity user) {
        this.comment = comment;
        this.user = user;
    }
}
