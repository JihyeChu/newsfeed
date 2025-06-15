package com.sparta.newsfeed.post.service;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.dto.req.ReqPostCreateDTO;
import com.sparta.newsfeed.post.dto.res.ResPostListDTO;
import com.sparta.newsfeed.post.entity.PostEntity;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.post.res.ResPostCreateDTO;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ResPostListDTO> getPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PostEntity> postEntityList = postRepository.findAll(pageable);

        return postEntityList.stream()
                .map(ResPostListDTO::of)
                .collect(Collectors.toList());
    }
}
