package com.yilin.www.spring.mvc.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yilin.www.spring.token2.CurrentUserMethodArgumentResolver;
import com.yilin.www.spring.token2.intercept.AuthorizationInterceptor;


/**
 * 配置类，增加自定义拦截器和解析器
 * @see com.scienjus.authorization.resolvers.CurrentUserMethodArgumentResolver
 * @see com.scienjus.authorization.interceptor.AuthorizationInterceptor
 * @author ScienJus
 * @date 2015/7/30.
 */
//@EnableWebMvc
//@Configuration
//@ComponentScan
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }
}