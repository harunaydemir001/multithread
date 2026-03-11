package com.harun.my_project.multithread;

public class _02_RunnableThreadExample {

    public static void main(String[] args) {
        Thread threadOne = new Thread(new Thread1());
        Thread threadTwo = new Thread(new Thread2());
        Thread threadThree = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("From demo 3 number : " + i);
            }
        });

        threadOne.start();
        threadTwo.start();
        threadThree.start();
    }
}

class Thread1 implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("From demo 1 number : " + i);
        }
    }
}

class Thread2 implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("From demo 2 number: " + i);
        }
    }
}
