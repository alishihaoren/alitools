package com.zn.learn.basic.javafature;

public class CheckFature {


    public static void main(String[] args) {
//        Son son = new Son();
//        son.say();
//        System.out.println(son.getId());

        TypeT t1=new TypeT<String>("alishihaoren");

        System.out.println(t1.getData());


        System.out.println("阿里".getBytes().length);
    }
}
