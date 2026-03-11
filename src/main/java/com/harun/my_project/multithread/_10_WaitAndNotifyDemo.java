package com.harun.my_project.multithread;

public class _10_WaitAndNotifyDemo {

    private static Object LOCK = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                one();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            two();
        });

        t1.start();
        t2.start();
    }

    private static void one() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello from method one!");
            LOCK.wait();
            System.out.println("Back again in the method one");
        }
    }

    private static void two() {
        synchronized (LOCK) {
            System.out.println("Hello from method two!");
            LOCK.notify();
            System.out.println("Hello from method two even after notifying");
        }
    }
}
