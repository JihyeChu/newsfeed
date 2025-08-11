package com.sparta.newsfeed.post.service;

import com.sparta.newsfeed.common.exception.BusinessException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.client.SearchClient;
import com.sparta.newsfeed.post.dto.req.ReqPostCreateDTO;
import com.sparta.newsfeed.post.dto.req.ReqPostPatchDTO;
import com.sparta.newsfeed.post.dto.res.ResPostListDTO;
import com.sparta.newsfeed.post.entity.PostEntity;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.post.dto.res.ResPostCreateDTO;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.validation.Valid;
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
    private final SearchClient searchClient;

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

        PostEntity savedPost = postRepository.save(postEntityForSaving);
        searchClient.index(List.of(ResPostListDTO.of(postEntityForSaving)));

        return ResPostCreateDTO.of(savedPost);
    }

    @Transactional(readOnly = true)
    public List<ResPostListDTO> getPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PostEntity> postEntityList = postRepository.findAllWithUser(pageable);

        return postEntityList.stream()
                .map(ResPostListDTO::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePost(@Valid ReqPostPatchDTO dto, Long id, Long userId) {
        PostEntity postEntityForUpdate = postRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_POST)
        );

        if (!userId.equals(postEntityForUpdate.getUser().getId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN_POST_UPDATE);
        }

        if (dto.getTitle() != null) {
            postEntityForUpdate.changeTitle(dto.getTitle());
        }

        if (dto.getContent() != null) {
            postEntityForUpdate.changeContent(dto.getContent());
        }
    }

    @Transactional
    public void deletePost(Long id, Long userId) {
        PostEntity postEntityForDelete = postRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_POST)
        );

        if (!userId.equals(postEntityForDelete.getUser().getId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN_POST_DELETE);
        }

        postEntityForDelete.markAsDeleted(postEntityForDelete.getUser().getNickname());
    }
}
