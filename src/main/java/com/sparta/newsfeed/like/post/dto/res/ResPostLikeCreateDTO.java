package com.sparta.newsfeed.like.post.dto.res;

import com.sparta.newsfeed.like.post.entity.PostLikeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPostLikeCreateDTO {

    private Long postId;
    private Long postLikeId;

    public static ResPostLikeCreateDTO of(PostLikeEntity postLikeEntity) {
        return ResPostLikeCreateDTO.builder()
                .postId(postLikeEntity.getPost().getId())
                .postLikeId(postLikeEntity.getId())
                .build();
    }
}
