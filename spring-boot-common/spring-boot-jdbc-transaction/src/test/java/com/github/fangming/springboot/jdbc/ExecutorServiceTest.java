package com.github.fangming.springboot.jdbc;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorServiceTest.class);

    @Test
    public void testThreadWaiting() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread subThread = new Thread(() -> {
            LOGGER.info("Sub thread executing...");
            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        subThread.start();
        //the main thread will wait until the sub thread finished
        subThread.join();
        long end = System.currentTimeMillis();
        LOGGER.info("Main Thread done, with execution time [{}] ms.", end - start);
    }

    @Test
    public void testMultipleThreadWaiting() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Thread> threads = Lists.newArrayList();
        for(int i =0; i < 10; i++) {
            threads.add(new Thread(() -> {
                LOGGER.info("Sub thread executing...");
                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        //the main thread will wait until the sub thread finished
        long end = System.currentTimeMillis();
        LOGGER.info("Main Thread done, with execution time [{}] ms.", end - start);
    }

    @Test
    public void testMultipleThreadWaiting2() throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i =0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                LOGGER.info("Sub thread executing...");
                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //Make sure the countdown method in the finally block to avoid any exception in the sub thread
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
        //the main thread will wait until the sub thread finished
        countDownLatch.await();
        long end = System.currentTimeMillis();
        LOGGER.info("Main Thread done, with execution time [{}] ms.", end - start);
    }


    @Test
    public void testFixedThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            int num = i;
            executorService.submit(() -> {
                LOGGER.info("thread:" + Thread.currentThread().getName() + ",time:" + FORMAT.format(new Date()) + ",num:" + num);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        while (!executorService.awaitTermination(10, TimeUnit.SECONDS));

        long end = System.currentTimeMillis();
        LOGGER.info("Main Thread done, with execution time [{}] ms.", end - start);
    }
}
