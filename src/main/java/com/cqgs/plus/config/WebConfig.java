package com.cqgs.plus.config;

import com.cqgs.plus.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截器，拦截 /user/** 路径下的所有请求
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/user/**")  // 拦截 /user/** 下的所有请求
                .addPathPatterns("/books/**")
                .excludePathPatterns("/user/login", "/user/register"); // 排除登录和注册接口
    }
}
