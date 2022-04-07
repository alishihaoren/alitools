package com.ali.basic.beans;

import java.time.LocalDateTime;

public class Student {

    private Integer id;
    private String name;
    private LocalDateTime tmp;

    private  String class_name;

    private Double score;
    private String city;

    public Student() {
    };

    public Student(Integer id, String name, String class_name, Double score, String city,LocalDateTime tmp) {
        this.id = id;
        this.name = name;
        this.class_name = class_name;
        this.score = score;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
