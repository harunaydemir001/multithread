package com.harun.my_project.multithread;

public class _09_SynchronisationProblemDemo {
    private static int counter1 = 0;
    private static int counter2 = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment1();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment2();
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("counter1=" + counter1 + "---" + "counter2=" + counter2);
    }

    private static synchronized void increment1() {
        counter1++;
    }

    private static synchronized void increment2() {
        counter2++;
    }
}
