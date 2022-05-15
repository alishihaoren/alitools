package com.zn.learn.basic.annotation;

@AliTestAnnotation(name="ali",data = 1.234)
public class AnnDemo {


    public   String name;
    private Integer age;

    @AliSingleAnnotation(name = "method")
    private  String data;

    @AliSingleAnnotation(name = "111")
    public void getDataInfo(@AliSingleAnnotation(name = "alishi haoren") String  name){
        System.out.println(" run getDataInfo "+name);
    }

    public AnnDemo() {
    }

    public AnnDemo(String name, Integer age, String data) {
        this.name = name;
        this.age = age;
        this.data = data;
    }

    private void getAnnINfo(){
        System.out.println("  data input stream ");
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
}
