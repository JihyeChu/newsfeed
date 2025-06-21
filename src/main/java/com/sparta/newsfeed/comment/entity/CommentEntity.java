package com.sparta.newsfeed.comment.entity;

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
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public CommentEntity(String content, PostEntity post, UserEntity user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public static CommentEntity create(String content, PostEntity postEntity, UserEntity userEntity) {
        return CommentEntity.builder()
                .content(content)
                .post(postEntity)
                .user(userEntity)
                .build();
    }

    public void update(String content) {
        this.content = content;
    }
}
