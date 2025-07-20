package com.sparta.newsfeed.like.comment.dto.res;

import com.sparta.newsfeed.like.comment.entity.CommentLikeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResCommentLikeCreateDTO {

    Long commentId;
    Long commentLikeId;

    public static ResCommentLikeCreateDTO of(CommentLikeEntity commentLikeEntity) {
        return ResCommentLikeCreateDTO.builder()
                .commentId(commentLikeEntity.getComment().getId())
                .commentLikeId(commentLikeEntity.getId())
                .build();
    }
}
