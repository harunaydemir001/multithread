package com.harun.my_project.multithread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class _19_BlockingQueueDemo {
    private static final int QUEUQU_CAPACITY = 10;
    static BlockingQueue<Integer> taskQueue = new ArrayBlockingQueue<>(QUEUQU_CAPACITY);

    public static void main(String[] args) {

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    taskQueue.put(i);
                    System.out.println("Task produced: " + i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread consumer1 = new Thread(() -> {
            while (true) {
                try {
                    int task = taskQueue.take();
                    processTask(task, "ConsumerOne");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread consumer2 = new Thread(() -> {
            while (true) {
                try {
                    int task = taskQueue.take();
                    processTask(task, "ConsumerTwo");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        producer.start();
        consumer1.start();
        consumer2.start();
    }

    private static void processTask(int task, String consumerName) throws InterruptedException {
        System.out.println("Task beign processed by " + consumerName + ": " + task);
        Thread.sleep(2000);
        System.out.println("Task consumed by " + consumerName + ": " + task);
    }
}