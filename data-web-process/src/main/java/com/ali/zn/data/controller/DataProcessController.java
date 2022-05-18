package com.ali.zn.data.controller;

import com.ali.zn.data.service.TagService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("/test")
public class DataProcessController {

    @Autowired
   private TagService tagService;

    @RequestMapping(value = "/ali")
    public String getMsg(HttpServletRequest request) {
        System.out.println( request.getHeader("name"));
       return JSON.toJSONString(tagService.getTagInfoById(Arrays.asList("110","111","112")));
//
//        System.out.println(JSON.toJSONString(request.getSession().getAttributeNames()));
//        return "alishihaoren";
    }

    ;
}