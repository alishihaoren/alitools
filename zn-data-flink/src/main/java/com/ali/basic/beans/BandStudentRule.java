package com.ali.basic.beans;

public class BandStudentRule {

    private Integer id;
    private String name;
    private Long tmp;
    private Double level;


    public BandStudentRule() {
    };

    public BandStudentRule(Integer id, String name, Double level, Long tmp) {
        this.id = id;
        this.name = name;
        this.tmp=tmp;

        this.level = level;

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
        return level;
    }

    public void setScore(Double score) {
        this.level = score;
    }

}
