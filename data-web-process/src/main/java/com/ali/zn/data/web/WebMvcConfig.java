package com.ali.zn.data.web;


import com.ali.zn.data.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * web相关配置
 *
 * @author zhou.xy
 * @since 1.0.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration=registry.addInterceptor(new UserLoginInterceptor());
//
//        registration.addPathPatterns("/**");
//        registration.excludePathPatterns("/login","/**/*.html","/**/*.css");
     ///  [{"children":[{"children":[{"id":"d-1-a-001","name":"车辆"},{"id":"d-1-a-002","name":"访客"}],"id":"device-001-a","name":"园区大门"},{"children":[{"id":"d-1-b-001","name":"湿度"},{"id":"d-1-b-002","name":"浇水"}],"id":"device-001-b","name":"1号草坪"}],"id":"device-001","name":"新智慧园区"},{"children":[{"children":[{"id":"d-2-a-001","name":"温度"},{"id":"d-2-a-002","name":"湿度"},{"id":"d-2-a-003","name":"空调"},{"id":"d-2-a-004","name":"智能窗帘"}],"id":"device-002-a","name":"客厅"},{"children":[{"id":"d-2-b-001","name":"温度"},{"id":"d-2-b-002","name":"湿度"},{"id":"d-2-b-003","name":"空调"}],"id":"device-002-b","name":"主卧"},{"children":[{"id":"d-2-c-001","name":"温度"},{"id":"d-2-c-002","name":"湿度"},{"id":"d-2-c-003","name":"空调"}],"id":"device-002-c","name":"次卧"},{"children":[{"id":"d-2-d-001","name":"温度"},{"id":"d-2-d-002","name":"湿度"},{"id":"d-2-d-003","name":"空调"}],"id":"device-002-d","name":"儿童房"},{"children":[{"id":"d-2-e-001","name":"温度"},{"id":"d-2-e-002","name":"湿度"},{"id":"d-2-e-003","name":"空调"}],"id":"device-002-e","name":"书房"}],"id":"device-002","name":"智能家居"},{"children":[{"children":[{"id":"d-3-a-001","name":"户数"},{"id":"d-3-a-002","name":"电压"},{"id":"d-3-a-003","name":"电流"}],"id":"device-003-a","name":"1号小区"},{"children":[{"id":"d-3-b-001","name":"浮点数"},{"id":"d-3-b-002","name":"布尔值"},{"id":"d-3-b-003","name":"0/1"},{"id":"d-3-b-004","name":"0-1"}],"id":"device-003-b","name":"2号小区"}],"id":"device-003","name":"电力能源"}]
    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** swagger配置 */
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("doc.html").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/META-INF/resources/webjars/");
    }

    private List excludePath() {
        return Arrays.asList("/doc.html/**", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }
}
