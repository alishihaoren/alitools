package com.zn.learn.basic.proxyReflect;

public class Student {
    private String id;
    private String name;
    private Integer age;

    private String Addr;
    private Double  score;

    public Student(String id, String name, Integer age, String addr, Double score) {
        this.id = id;
        this.name = name;
        this.age = age;
        Addr = addr;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
