package com.zerobase.bob.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final static String PATH = "/files/**";
    private final static String LOCATION = "file:/Users/krystal/IdeaProjects/bob/files/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler(PATH)
                .addResourceLocations(LOCATION);
    }
}