package com.sparta.newsfeed.user.dto.res;

import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResUserGetProfileDTO {

    private Long id;
    private String nickname;
    private String username;
    private String email;

    public static ResUserGetProfileDTO of(UserEntity userEntity) {
        return ResUserGetProfileDTO.builder()
                .id(userEntity.getId())
                .nickname(userEntity.getNickname())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }
}
