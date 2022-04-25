package com.zn.learn.basic.annotation;

@AliTestAnnotation(name="ali",data = 1.234)
public class AnnDemo {

    @AliSingleAnnotation(name = "method")
    private  String data;
    private void getAnnINfo(){
        System.out.println("  data input stream ");
    }

}
