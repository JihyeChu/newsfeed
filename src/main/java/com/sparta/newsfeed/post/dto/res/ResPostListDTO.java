package com.sparta.newsfeed.post.dto.res;

import com.sparta.newsfeed.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPostListDTO {

    private Long id;
    private String title;
    private String content;

    public static ResPostListDTO of(PostEntity postEntity) {
        return ResPostListDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .build();
    }
}
