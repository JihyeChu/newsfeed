package com.sparta.newsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.sparta.newsfeed.post")
public class NewsfeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsfeedApplication.class, args);
    }

}
