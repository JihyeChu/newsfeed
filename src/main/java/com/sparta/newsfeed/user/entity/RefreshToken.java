package com.sparta.newsfeed.user.entity;

import com.sparta.newsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_tokens")
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public RefreshToken(String nickname, String refreshToken) {
        this.nickname = nickname;
        this.refreshToken = refreshToken;
    }

    public static RefreshToken create(String nickname, String refreshToken) {
        return RefreshToken.builder()
                .nickname(nickname)
                .refreshToken(refreshToken)
                .build();
    }
}
