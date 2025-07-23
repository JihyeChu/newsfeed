package com.sparta.newsfeed.post.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqPostPatchDTO {

    private String title;
    private String content;

}
