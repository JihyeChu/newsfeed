package com.sparta.newsfeed.user.security;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByNicknameAndDeletedAtNull(nickname).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        return new CustomUserDetails(userEntity);
    }
}
