package com.sparta.newsfeed.user.dto.res;

import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResUserPostSignupDTO {

    private Long id;

    public static ResUserPostSignupDTO of(UserEntity userEntity) {
        return ResUserPostSignupDTO.builder()
                .id(userEntity.getId())
                .build();
    }
}
