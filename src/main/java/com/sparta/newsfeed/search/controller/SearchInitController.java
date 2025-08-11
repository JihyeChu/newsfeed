package com.sparta.newsfeed.search.controller;

import com.sparta.newsfeed.search.service.AdminSearchInitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchInitController {

    private final AdminSearchInitService adminSearchInitService;

    @PostMapping("/admin/init-search")
    public void initSearch() {
        adminSearchInitService.initSearch();
    }
}
