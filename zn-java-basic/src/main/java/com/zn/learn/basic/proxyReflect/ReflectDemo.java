package com.zn.learn.basic.proxyReflect;

import java.lang.reflect.Field;

public class ReflectDemo {
    /**
     * 反射机制是通过
     *
     * @param args
     */


    public static void main(String[] args) {
        Class stclass = Student.class;
        Field[] fields = stclass.getDeclaredFields();




    }

}
