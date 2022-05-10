package com.zn.learn.basic.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

public class SemaPhoreTest {

    /**
     * Semaphore(信号量) 和 synchronized 类似，是控制线程能否进入某一同步代码区的一种手段，但是 synchronized 每次只有一个进程可以进入同步代码区，而 Semaphore可以指定多个线程同时访问某个资源。
     * <p>
     * CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行
     */
    public static void main(String[] args) {

//        Semaphore semaphore = new Semaphore(3);
//        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            int finali = i;
//            threadPoolExecutor.execute(() -> {
//                try {
//                    semaphore.acquire();
//                    System.out.println("Index:" + finali);
//                    Thread.sleep(2000);
//                    semaphore.release();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        threadPoolExecutor.shutdown();
//
//
//        CountDownLatch countDownLatch = new CountDownLatch(3);
//
//        ExecutorService es = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 10; i++) {
//
//          es.execute(()->{
//
//              System.out.println("thread name  "+Thread.currentThread().getName());
//              try {
//                  Thread.sleep(3000);
//              } catch (InterruptedException e) {
//                  e.printStackTrace();
//              }
//              countDownLatch.countDown();
//          });
//        }
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        es.shutdown();

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("barrierAction merge data"));
        for (int i = 0; i < 5; i++) {
            int finali = i;
            threadPool.submit(() -> {
                try {
                    System.out.println("Task 1 Begin Index:" + finali);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10000));
                    System.out.println("Task 1 Finished Index:" + finali);
                    cyclicBarrier.await();
                    System.out.println("Task 2 Begin Index:" + finali);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10000));
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        };



    }


}
