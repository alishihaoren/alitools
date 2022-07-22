package com.java.basic.test;

import org.junit.Test;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class ScheduleTest {


    @Test
    public void getScheduleINfo() {
        String cron = "0/10 * * * * ?";

        String cron2 = "0/3 * * * * ?";
        Map<String, ScheduledFuture<?>> scheduledFutureMap = new HashMap<>();
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        ScheduledFuture<?> schedule=  executor.schedule(new MyTask(), new CronTrigger(cron));

        ScheduledFuture<?> schedule2=   executor.schedule(new MyTask2(), new CronTrigger(cron2));
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        schedule2.cancel(true);



        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }






    }


    class MyTask implements Runnable {


        @Override
        public void run() {
            System.out.println(" TASK 1 executor -----"+ LocalDateTime.now());
        }
    }

    class MyTask2 implements Runnable {


        @Override
        public void run() {
            System.out.println(" TASK2  executor -----"+ LocalDateTime.now());
        }
    }
}
