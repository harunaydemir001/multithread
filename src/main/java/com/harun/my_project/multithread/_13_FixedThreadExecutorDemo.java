package com.harun.my_project.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _13_FixedThreadExecutorDemo {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            for (int i = 0; i < 7; i++) {
                executorService.execute(new Work(i));
            }
        }
    }
}

class Work implements Runnable {
    private final int workId;

    Work(int workId) {
        this.workId = workId;
    }

    @Override
    public void run() {
        System.out.println("Task Id " + workId + " beign executed by thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
