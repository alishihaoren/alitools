package com.zn.learn.basic.collection;

import com.zn.learn.basic.annotation.AliTestAnnotation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MapData {
    /**
     * 集合分为两类
     * Collection接口：单列数据，定义存取一组对象的方法的集合
     * LIst：元素有序、可重复的集合
     * Set：元素无序、不可重复的集合
     * Map接口：双列数据，保存具有映射关系“Key-value对”的集合
     */


    public void HashMapCodeLearn() {
        Map<String, String> map = new HashMap<>();


    }


    public static void getConcurrentHashMap() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(100, (float) 0.75);

  concurrentHashMap.put("12","124");
    }

    public static void forDataTest() {
        String[] ali = {"1", "2", "3"};
        for (String[] key = ali; ; ) {

            System.out.println(key);
        }


    }

    /**
     * 链表结构 双向链表  头尾出现
     */
    public  static  void getLinkedListMap(){
        LinkedHashMap  linkedHashMap=new LinkedHashMap();

        linkedHashMap.put("aaa","bbb");




    }


    public static void main(String[] args) {
        Hashtable   hashtable= new Hashtable();
    }


}
