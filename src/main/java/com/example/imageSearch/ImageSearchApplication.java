package com.example.imageSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ImageSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageSearchApplication.class, args);
    }

}
