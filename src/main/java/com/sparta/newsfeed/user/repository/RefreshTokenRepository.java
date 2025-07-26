package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByNickname(String nickname);

}
