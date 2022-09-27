package com.imagegallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;


@SpringBootApplication
public class ImageGalleryApplication {

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    public static void main(String... args) {
        SpringApplication.run(ImageGalleryApplication.class, args);

    }



}
