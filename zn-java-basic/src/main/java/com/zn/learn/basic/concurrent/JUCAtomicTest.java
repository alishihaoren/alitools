package com.zn.learn.basic.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class JUCAtomicTest {


    /**
     * Atomic  :采用 CAS  + volatile 方式进行数据的更新 其底层 采用内存屏障指令进行，同时存
     * volatile采用 memory barrier 内存屏障保障 数据的可读性
     *   sun.misc.Unsafe;
     */

    @Test
    public  void  getAtomicInteger(){
        AtomicInteger  atomicInteger=new AtomicInteger();
        AtomicReference<String> atomicReference=new AtomicReference<>();


    }



}
