package com.harun.my_project.multithread;

import java.util.concurrent.CountDownLatch;

public class _18_Restaurant {
    public static void main(String[] args) throws InterruptedException {
        int numberOfChefs = 3;
        CountDownLatch countDownLatch = new CountDownLatch(numberOfChefs);
        new Thread(new Chef("Chef A", "Pizza", countDownLatch)).start();
        new Thread(new Chef("Chef B", "Pasta", countDownLatch)).start();
        new Thread(new Chef("Chef C", "Salad", countDownLatch)).start();

        countDownLatch.await();

        System.out.println("All dishes are ready! Lets start serving customers.");
    }
}

class Chef implements Runnable {
    private final String name;
    private final String dish;
    private final CountDownLatch latch;

    Chef(String name, String dish, CountDownLatch latch) {
        this.name = name;
        this.dish = dish;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is preparing " + dish);
            Thread.sleep(2000);
            System.out.println(name + " has finished preparing " + dish);
            latch.countDown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
