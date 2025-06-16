package com.sparta.newsfeed.post.dto.res;

import com.sparta.newsfeed.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPostCreateDTO {

    private Long id;
    private String title;
    private String content;

    public static ResPostCreateDTO of(PostEntity postEntity) {
        return ResPostCreateDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .build();
    }
}
