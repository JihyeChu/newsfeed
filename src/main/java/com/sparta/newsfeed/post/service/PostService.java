package com.sparta.newsfeed.post.service;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.dto.req.ReqPostCreateDTO;
import com.sparta.newsfeed.post.entity.PostEntity;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.post.res.ResPostCreateDTO;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResPostCreateDTO createPost(ReqPostCreateDTO dto, Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );

        PostEntity postEntityForSaving = PostEntity.create(
                dto.getTitle(),
                dto.getContent(),
                userEntity
        );

        return ResPostCreateDTO.of(postRepository.save(postEntityForSaving));
    }
}
