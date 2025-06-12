package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.common.utils.JwtUtil;
import com.sparta.newsfeed.user.dto.req.ReqUserDeleteAccountDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPostSignupDTO;
import com.sparta.newsfeed.user.dto.req.ReqUserPatchProfileDTO;
import com.sparta.newsfeed.user.dto.res.ResUserGetProfileDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostLoginDTO;
import com.sparta.newsfeed.user.dto.res.ResUserPostSignupDTO;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.entity.UserRole;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
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

    @Transactional
    public ResUserPostLoginDTO login(@Valid ReqUserPostLoginDTO dto) {

        UserEntity userEntity = getUserEntityOptionalByNickname(dto.getNickname()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }

        String jwtToken = jwtUtil.generateToken(userEntity.getNickname());

        return ResUserPostLoginDTO.of(jwtToken);
    }

    @Transactional(readOnly = true)
    public ResUserGetProfileDTO getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        return ResUserGetProfileDTO.of(userEntity);
    }

    @Transactional
    public void updateProfile(@Valid ReqUserPatchProfileDTO dto, Long id) {
        UserEntity userEntityForUpdate = userRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        // 1. 비밀번호 변경 요청이 있는지 확인
        if (dto.getNewPassword() != null || dto.getCurrentPassword() != null) {
            // 1-1. 만약 비밀번호 수정을 희망한다면 현재 비밀번호(입력값)가 null 또는 빈 값이면 예외
            if (dto.getCurrentPassword() == null) {
                throw new BusinessException(ErrorCode.REQUIRED_CURRENT_PASSWORD);
            }
            // 1-2. 현재 비밀번호가 실제 비밀번호와 일치하는지 확인
            if (!passwordEncoder.matches(dto.getCurrentPassword(), userEntityForUpdate.getPassword())) {
                throw new BusinessException(ErrorCode.INVALID_CURRENT_PASSWORD);
            }
            // 1-3. 새 비밀번호가 기존 비밀번호와 동일한지 확인
            if (passwordEncoder.matches(dto.getNewPassword(), userEntityForUpdate.getPassword())) {
                throw new BusinessException(ErrorCode.SAME_AS_OLD_PASSWORD);
            }

            // 1-4. 비밀번호 변경
            userEntityForUpdate.changePassword(passwordEncoder.encode(dto.getNewPassword()));
        }

        if (dto.getNickname() != null) {
            checkNicknameDuplication(dto.getNickname());
            userEntityForUpdate.changeNickname(dto.getNickname());
        }

        if (dto.getUsername() != null) {
            checkNicknameDuplication(dto.getUsername());
            userEntityForUpdate.changeUsername(dto.getUsername());
        }

        if (dto.getEmail() != null) {
            userEntityForUpdate.changeEmail(dto.getEmail());
        }
    }

    @Transactional
    public void deleteAccount(ReqUserDeleteAccountDTO dto, Long id) {
        UserEntity userEntityForDelete = userRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        if (!passwordEncoder.matches(dto.getCurrentPassword(), userEntityForDelete.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }

        userEntityForDelete.markAsDeleted(userEntityForDelete.getNickname());
    }

    private void checkNicknameDuplication(String nickname) {
        getUserEntityOptionalByNickname(nickname)
                .ifPresent(user -> {
                    throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
                });
    }

    private Optional<UserEntity> getUserEntityOptionalByNickname(String nickname) {
        return userRepository.findByNicknameAndDeletedAtNull(nickname);
    }
}
