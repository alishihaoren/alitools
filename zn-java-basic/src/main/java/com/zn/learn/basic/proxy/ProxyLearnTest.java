package com.zn.learn.basic.proxy;

public class ProxyLearnTest {


}


class Student {

    private String name;
    private Long id;
    private String sex;

    private String addr;

    public Student() {
    }

    public Student(String name, Long id, String sex, String addr) {
        this.name = name;
        this.id = id;
        this.sex = sex;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}