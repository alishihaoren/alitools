package com.ali.zn.data.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class DataProcessController {

    @RequestMapping(value = "/ali")
    public String getMsg(HttpServletRequest request) {
        System.out.println( request.getHeader("name"));

        System.out.println(JSON.toJSONString(request.getSession().getAttributeNames()));
        return "alishihaoren";
    }

    ;
}