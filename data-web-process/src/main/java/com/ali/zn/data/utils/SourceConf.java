package com.ali.zn.data.utils;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SourceConf {


    private  int age=100;

    SourceConf() {
        System.out.println("------  haha  Ilearn bean create ----");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}