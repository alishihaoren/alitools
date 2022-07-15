package com.ali.zn.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.ali.zn.data.*")
@MapperScan(basePackages = "com.ali.zn.data.mapper")
@RefreshScope
public class DataProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataProcessApplication.class, args);
    }

    // 直接部署镜像 直接部署

}
