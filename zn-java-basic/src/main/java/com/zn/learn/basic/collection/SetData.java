package com.zn.learn.basic.collection;

import com.alibaba.fastjson.JSON;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class SetData {

    public static void main(String[] args) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();


        /** HashMap<E,Object> map;
         *  采用  HashMap 作为  底层的存储结构， 散列表，数组
         */
        HashSet hashSet = new HashSet();
        hashSet.add("111");


        /**
         *  1 treeSet 采用  TreeMap  作为底层的存储结构 enteryMap  红黑树结构
         *  2 采用红黑树作为存储
         *
         */
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("aaa");
        treeSet.add("bbb");
        treeSet.add("ccc");
        treeSet.add("aaa");
        treeSet.add("eee");
        treeSet.size();
        System.out.println(JSON.toJSONString(treeSet));
        /**
         * LinkedHashMap 作为底层的存储结构
         */

        LinkedHashSet linkedHashSet1=new LinkedHashSet();


    }
}
