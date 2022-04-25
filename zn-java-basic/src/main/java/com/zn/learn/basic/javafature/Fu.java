package com.zn.learn.basic.javafature;

import java.util.List;

public class Fu {

    private String name="fu";
    private String id="123";

    public Fu() {
    }

    public  String say(){
        System.out.println(" father");
        return "my your father ";
    }

    public Fu(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public  <T>  String getNUms(T t1){
        return "1111";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
