package com.zn.learn.basic.collection;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.TreeSet;

public class ListData {

    public static void main(String[] args) {

        LinkedList<String>  datalist=new LinkedList<>();
        datalist.add("aaa");
        datalist.add("eee");
        datalist.add("aaa");

        System.out.println(JSON.toJSONString(datalist));




    }
}
