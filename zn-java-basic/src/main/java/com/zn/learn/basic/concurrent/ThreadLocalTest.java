package com.zn.learn.basic.concurrent;

import java.util.concurrent.*;

public class ThreadLocalTest {

    public static void main(String[] args) {
//        ThreadLocal<Integer> t1 = new ThreadLocal<>();
//
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            int finali = i;
//            executorService.execute(() -> {
//                t1.set(finali);
//                try {
//                    Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(" data show thread --- " + Thread.currentThread().getName() + "  " + t1.get());
//
//            });
//        }
//
//        executorService.shutdown();

        ThreadPoolExecutor  tpe=new ThreadPoolExecutor(1,3,2000, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        for(int i=0;i<13;i++){
            tpe.execute(()->{
                System.out.println(tpe.getActiveCount()+"  "+Thread.currentThread().getName()+"  "+tpe.getQueue().size());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("active  thread nums "+ tpe.getActiveCount());
        try {
            Thread.sleep(10000);
            System.out.println("active  thread nums "+ tpe.getActiveCount());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
