package com.memnik.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.memnik.common.constants.Constants.STORAGE;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/%s/**".formatted(STORAGE))
                .addResourceLocations("file:%s/".formatted(STORAGE));
    }
}
