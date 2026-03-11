package com.harun.my_project.multithread;

public class _08_SynchronisationDemo {
    private static int counter = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
            System.out.println("counter: " + counter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static synchronized void increment() {
        counter++;
    }
}
