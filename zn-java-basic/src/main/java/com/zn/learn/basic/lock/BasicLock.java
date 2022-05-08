package com.zn.learn.basic.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * synchronized JVM 级别
 * Lock
 *  原子性
 * 即一个操作或者多个操作，要么全部执行并且执行的过程不会被任何因素打断，要么就都不执行。原子性就像数据库里面的事务一样，他们是一个团队，同生共死。
 */
public class BasicLock {




    /**
     * synchrinized
     * 监视器，操作系统的监视器锁monitor,
     */

    private synchronized  void getSysInfo(){
        synchronized (""){

        }

    }


    /**
     * volatile  关键字  可读性与顺序性
     *  1.volatile可以保证变量的可见性。    2.保证有序性。（防止指令重排）
     *   不能保证原子性，还需要 用锁机制来保证
     *
     */

    private volatile  int nums;
    /**
     *可重入锁，悲观锁，
     *
     * 可中断，异常不释放锁
     *
     * AQS abstractQueueSynchronized
     */
    private static Lock lock = new ReentrantLock();
    /**
     * 读写锁， 读写为乐观锁，
     */

    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    public  static  void getThreadNums(){
        lock.lock();
        try {
            try {
                // 3.支持多种加锁方式，比较灵活; 具有可重入特性
                try {
                    System.out.println(" name ----"+Thread.currentThread().getName());
                    Thread.sleep(5000);
                    if(lock.tryLock(3000, TimeUnit.MILLISECONDS)){ }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                // 4.手动释放锁
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                getThreadNums();
            }
        },"ali1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                getThreadNums();
            }
        },"ali2").start();
    }

}
