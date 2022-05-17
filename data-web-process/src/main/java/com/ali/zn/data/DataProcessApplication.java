package com.ali.zn.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ali.zn.data.controller")
public class DataProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataProcessApplication.class, args);
    }

    // 直接部署镜像 直接部署

}
