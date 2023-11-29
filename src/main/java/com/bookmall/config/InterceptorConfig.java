package com.bookmall.config;

import com.bookmall.interceptor.AuthorityInterceptor;
import com.bookmall.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 配置拦截器， 配置以下的两个内容
 * JwtInterceptor是一个用于验证JWT令牌的拦截器。
 * AuthorityInterceptor是一个用于权限验证的拦截器。
 * 通过这个配置类，将拦截器添加到了Spring MVC的拦截器链中，以实现对请求的JWT令牌验证和权限验证。
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    JwtInterceptor jwtInterceptor;
    @Resource
    AuthorityInterceptor authorityInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //jwt拦截器
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register","/file/**","/avatar/**","/api/good/**","/api/icon/**","/api/category/**","/api/market/**","/api/carousel/**")
                .order(0)
        ;
        //权限校验拦截器
        registry.addInterceptor(authorityInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns()
                .order(1)
        ;
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
