package com.sparta.newsfeed.post.client;

import com.sparta.newsfeed.post.dto.res.ResPostListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "searchClient", url = "http://localhost:8081")
public interface SearchClient {

    @PostMapping("/api/index")
    ResponseEntity<Void> index(@RequestBody List<ResPostListDTO> documents);

}
