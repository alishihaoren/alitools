package com.ali.zn.data.conf;

import com.ali.zn.data.interceptor.UserLoginInterceptor;
import org.apache.catalina.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration=registry.addInterceptor(new UserLoginInterceptor());

        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/login","/**/*.html","/**/*.css");

    }
}
