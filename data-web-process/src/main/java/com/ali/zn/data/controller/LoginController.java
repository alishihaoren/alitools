package com.ali.zn.data.controller;

import com.ali.zn.data.pojo.dto.UserInfo;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ui/login")
public class LoginController {

    @Value("${datasource.username}")
    private String username;


    @RequestMapping(value = "/user")
    public Object getLoginToken() {

        System.out.println("----------nacos---------"+username);
        return "1111";
    }


}
