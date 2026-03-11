package com.harun.my_project.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _12_SingleExecutorDemo {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            for (int i = 1; i <= 5; i++) {
                executorService.execute(new Task(i));
            }
        }
    }
}

class Task implements Runnable {

    private final int taskId;

    Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task " + taskId + " beign executed by Thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
