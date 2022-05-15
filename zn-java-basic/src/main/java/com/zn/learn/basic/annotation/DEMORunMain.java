package com.zn.learn.basic.annotation;

import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DEMORunMain {

    public static void main(String[] args) {

        AnnDemo annDemo1=new AnnDemo();
        Class<AnnDemo> anCl=null;
//        Class<AnnDemo> anCl = (Class<AnnDemo>) annDemo1.getClass();
        try {
            anCl= (Class<AnnDemo>) Class.forName("com.zn.learn.basic.annotation.AnnDemo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        AnnDemo annDemo= null;
        try {
            annDemo = anCl.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //
        System.out.println(anCl.getAnnotation(AliTestAnnotation.class).data());
        // 反射获作用到方法上注解值
        try {
            Method method = anCl.getMethod("getDataInfo",String.class);
            System.out.println(method.invoke(annDemo,"-----------   1"));

            AliSingleAnnotation aliSingleAnnotation = method.getAnnotation(AliSingleAnnotation.class);
            System.out.println(aliSingleAnnotation.name());
            Parameter[] parameters = method.getParameters();
            for (Parameter p1 : parameters) {
                System.out.println( "params    "+p1.getName());
                System.out.println( p1.getAnnotation(AliSingleAnnotation.class).name());
            }


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 获取作用到类名上的注解值
        AliTestAnnotation aliTestAnnotation = (AliTestAnnotation) anCl.getAnnotation(AliTestAnnotation.class);
        System.out.println(aliTestAnnotation.name() + "   " + aliTestAnnotation.data());
        //

        // 获取字段
        Field[] fields = anCl.getDeclaredFields();
        for (Field f1 : fields) {
            System.out.println(f1.getName());
//                System.out.println( f1.get(f1.getName()));
        }


        // 获取方法
//        Method[] methods = anCl.getMethods();
//        for (Method method : methods) {
//            System.out.println(method.getParameterTypes()[1].getPackage());
//        }
//
//        Annotation[] annArr = anCl.getAnnotations();
//        for (Annotation ann : annArr) {
//            System.out.println(ann.annotationType());
//            System.out.println(ann.toString());
//
//        }


    }

}
