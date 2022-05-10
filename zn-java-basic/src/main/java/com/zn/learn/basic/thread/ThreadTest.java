package com.zn.learn.basic.thread;


import org.junit.Test;

import java.util.concurrent.*;

/**
 * 创建多线程 四种方式
 * 1 new thread()匿名 run类  调用start() 启动
 * 2 new runnable(),实现接口， 然后置于 thread 中运行
 * 3 Callable 方法 会掉 ，通过 taskFultre 异步调用 返回结果
 * 4 ThreadPool 实现  线程池模式
 */

public class ThreadTest {

    @Test
    public void getThreadDemo() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(" running thread ---" + Thread.currentThread().getName());
            }
        });
        t1.start();

    }

    /**
     * runnable  是一个接口 ，匿名内部类实现
     */
    @Test
    public void getRunnable() {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("run runable data " + Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(r1, "running demo thread1 ");
        t1.start();
    }

    /**
     * 线程池实现 Executors  实现创建线程池存在 OOM 问题，最大线程数据保持为 Interger.MAX_VALUE导致角度资源问题 不推荐
     * 一般采用ThreaPoolExecutor 进行线程池的定义
     * 线程阻塞策略：
     * ArrayBlockingQueue;
     * LinkedBlockingQueue;
     * SynchronousQueue;
     */
    @Test
    public void getThreadPoolTest() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 30, 7000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("1111");
            }
        });
//      threadPool.shutdown();
    }

    /**
     *
     */

    @Test
    public void getCallableTest() {

        Callable callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return " give me  answer ";
            }
        };
        FutureTask futureTask = new FutureTask(callable);
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
/**
 * 线程对其策略
 * countDownLunch
 *
 */
}
