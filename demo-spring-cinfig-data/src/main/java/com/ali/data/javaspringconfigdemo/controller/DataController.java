package com.ali.data.javaspringconfigdemo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Value("${datasource.username}")
    private String number;

    @GetMapping("/getNumber")
    public String getNumber(){
        return number;
    }
}
