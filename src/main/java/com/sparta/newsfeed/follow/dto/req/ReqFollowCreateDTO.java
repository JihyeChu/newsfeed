package com.sparta.newsfeed.follow.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqFollowCreateDTO {

    @NotNull(message = "팔로우 대상 회원 ID는 필수입니다.")
    private Long followeeId;

}
