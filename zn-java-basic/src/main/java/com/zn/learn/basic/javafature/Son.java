package com.zn.learn.basic.javafature;

public class Son extends Fu {
    private String name="son";


//    public  String say(){
//        System.out.println("son");
//        return  " my name is son";
//
//    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Son() {
    }


    public Son(String name, String id, String name1) {
        super(name, id);
        this.name = name1;
    }
}
