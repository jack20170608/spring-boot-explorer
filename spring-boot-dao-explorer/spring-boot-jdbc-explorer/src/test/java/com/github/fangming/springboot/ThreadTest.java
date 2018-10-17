package com.github.fangming.springboot;

import org.junit.Test;

public class ThreadTest {

    @Test
    public void testThreadNotifyAndWait() throws InterruptedException {
        Object monitor1 = new Object();
        Object monitor2 = new Object();
        boolean[] states = {false, false};

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start...");
            try {
                synchronized (monitor1) {
                    while (!states[0]) {
                        monitor1.wait();
                    }
                }
                System.out.println("Querying data........");
                synchronized (monitor2){
                    states[1] = true;
                    monitor2.notifyAll();
                }
                System.out.println(Thread.currentThread().getName() + " done...");
            }catch(Exception e){
                System.out.println("some error happen.");
                e.printStackTrace();
            }
        }, "DataQueryThread");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start...");
            try {
                System.out.println("Updating data");
                states[0] = true;
                synchronized (monitor1){
                    monitor1.notify();
                }
                System.out.println("Updated..");

                synchronized (monitor2){
                    while(!states[1]){
                        monitor2.wait();
                    }
                }
                System.out.println("Commited ..");
                System.out.println(Thread.currentThread().getName() + " done...");
            }catch (Exception e){
                System.out.println("some error happen.........");
                e.printStackTrace();
            }
        }, "DataUpdateThread");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Testing done............");
    }
}
