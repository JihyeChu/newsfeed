package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.user.dto.req.ReqUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostSignupDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostSignupDTO;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.entity.UserRole;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public ResUserPostSignupDTO signup(@Valid ReqUserPostSignupDTO dto) {

        checkNicknameDuplication(dto.getNickname());

        UserEntity userEntityForSaving = UserEntity.create(
                dto.getNickname(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getUsername(),
                dto.getEmail(),
                UserRole.USER
        );

        return ResUserPostSignupDTO.of(userRepository.save(userEntityForSaving));
    }

    private void checkNicknameDuplication(String nickname) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByNicknameAndDeletedAtNull(nickname);
        if (optionalUserEntity.isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
