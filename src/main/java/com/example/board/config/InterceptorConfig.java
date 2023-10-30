package com.example.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.board.interceptor.SignInCheckInterceptor;

// @Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private SignInCheckInterceptor signInCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInCheckInterceptor)
                .addPathPatterns("/**")
<<<<<<< HEAD
                .excludePathPatterns("/signin", "/signup", "/", "/loginnoti", "/*.png", "/email-check", "/*.jpg","/popupmembership");
=======
                .excludePathPatterns("/signin", "/signup", "/", "/loginnoti", "/*.png", "/email-check", "/*.jpg","/home");
>>>>>>> 988f893ae63f5226bb533e9c231baceeb8f9ecb4
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}