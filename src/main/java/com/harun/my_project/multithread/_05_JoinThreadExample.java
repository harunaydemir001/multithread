package com.harun.my_project.multithread;

public class _05_JoinThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Thread one : " + i);
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Thread two : " + i);
            }
        });

        System.out.println("Before executing the threads");
        one.start();
        two.start();
        one.join();
        two.join();

        System.out.println("Done executing the threads");
    }
}
