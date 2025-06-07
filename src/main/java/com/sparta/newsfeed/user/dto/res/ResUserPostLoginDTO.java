package com.sparta.newsfeed.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResUserPostLoginDTO {

    private String token;

    public static ResUserPostLoginDTO of(String token) {
        return ResUserPostLoginDTO.builder()
                .token(token)
                .build();
    }

}
