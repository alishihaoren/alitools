package com.ali.basic.beans;

import java.time.LocalDateTime;

public class Student {

    private Integer id;
    private String name;
    private Long tmp;
    private Double score;


    public Student() {
    };

    public Long getTmp() {
        return tmp;
    }

    public void setTmp(Long tmp) {
        this.tmp = tmp;
    }

    public Student(Integer id, String name, Double score, Long tmp) {
        this.id = id;
        this.name = name;
        this.tmp=tmp;

        this.score = score;

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


    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
