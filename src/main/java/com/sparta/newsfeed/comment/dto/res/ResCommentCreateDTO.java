package com.sparta.newsfeed.comment.dto.res;

import com.sparta.newsfeed.comment.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResCommentCreateDTO {

    private Long commentId;
    private Long postId;
    private String content;

    public static ResCommentCreateDTO of(CommentEntity commentEntity) {
        return ResCommentCreateDTO.builder()
                .commentId(commentEntity.getId())
                .postId(commentEntity.getPost().getId())
                .content(commentEntity.getContent())
                .build();
    }
}
