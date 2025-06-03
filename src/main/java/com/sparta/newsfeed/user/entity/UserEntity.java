package com.sparta.newsfeed.user.entity;

import com.sparta.newsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    private UserRole role;

    @Builder
    public UserEntity(String nickname, String password, String username, String email, UserRole role) {
        this.nickname = nickname;
        this.password = password;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public static UserEntity create(String nickname, String password, String username, String email, UserRole role) {
        return UserEntity.builder()
                .nickname(nickname)
                .password(password)
                .username(username)
                .email(email)
                .role(role)
                .build();
    }
}
