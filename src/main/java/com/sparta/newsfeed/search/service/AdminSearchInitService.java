package com.sparta.newsfeed.search.service;

import com.sparta.newsfeed.post.client.SearchClient;
import com.sparta.newsfeed.post.dto.res.ResPostListDTO;
import com.sparta.newsfeed.post.entity.PostEntity;
import com.sparta.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSearchInitService {

    private final PostRepository postRepository;
    private final SearchClient searchClient;

    @Transactional(readOnly = true)
    public void initSearch() {
        List<PostEntity> postList = postRepository.findAllWithUser();
        List<ResPostListDTO> dtoList = postList.stream()
                .map(ResPostListDTO::of)
                .toList();

        searchClient.index(dtoList);
    }
}
