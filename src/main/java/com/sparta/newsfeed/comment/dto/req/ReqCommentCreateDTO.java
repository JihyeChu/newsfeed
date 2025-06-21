package com.sparta.newsfeed.comment.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqCommentCreateDTO {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    public String content;

}
