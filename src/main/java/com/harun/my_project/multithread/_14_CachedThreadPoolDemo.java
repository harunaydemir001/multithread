package com.harun.my_project.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _14_CachedThreadPoolDemo {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 1000; i++) {
                executorService.execute(new TaskOne(i));
            }
        }
    }
}

class TaskOne implements Runnable {
    private final int taskId;

    TaskOne(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task ID " + taskId + "beign executed by Thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}