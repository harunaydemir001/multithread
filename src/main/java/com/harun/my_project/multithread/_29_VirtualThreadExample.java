package com.harun.my_project.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class _29_VirtualThreadExample {

    public static void main(String[] args) throws InterruptedException {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();) {
            for (int i = 0; i < 100000; i++) {
                int requestId = i;
                executor.submit(() -> handleRequest(requestId));
            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }
    }

    private static void handleRequest(int id) {
        try {
            Thread.sleep(1000);
            System.out.println("Handled request: " + id + " by " + Thread.currentThread());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class PlatformThreadExample {

    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newFixedThreadPool(100000)) {
            for (int i = 0; i < 100000; i++) {
                int requestId = i;
                executor.submit(() -> handleRequest(requestId));
            }
            executor.shutdown();
        }
    }

    private static void handleRequest(int id) {
        try {
            // IO simülasyonu
            Thread.sleep(1000);
            System.out.println("Handled request: " + id + " by " + Thread.currentThread());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}