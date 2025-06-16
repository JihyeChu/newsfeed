package com.sparta.newsfeed.follow.service;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.follow.dto.req.ReqFollowCreateDTO;
import com.sparta.newsfeed.follow.entity.FollowEntity;
import com.sparta.newsfeed.follow.repository.FollowRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void createFollow(ReqFollowCreateDTO dto, Long loginUserId) {
        // 팔로우 할 사람은 누구인지
        UserEntity followerEntity = userRepository.findById(loginUserId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        // 팔로우 당할 사람은 누구인지
        UserEntity followeeEntity = userRepository.findById(dto.getFolloweeId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        FollowEntity followEntityForSaving = FollowEntity.create(followerEntity, followeeEntity);

        followRepository.save(followEntityForSaving);
    }
}
