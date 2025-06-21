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
public class ResCommentListDTO {

    private Long commentId;
    private Long postId;
    private String comment;

    public static ResCommentListDTO of(CommentEntity comment) {
        return ResCommentListDTO.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .comment(comment.getContent())
                .build();
    }
}
