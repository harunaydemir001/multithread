package com.harun.my_project.multithread;

public class _07_ThreadPriortiyExample {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        System.out.println(Thread.currentThread().getPriority());
    }
}

class _07_ThreadPriortiyExample2 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " says Hi!");

        Thread one = new Thread(() -> System.out.println(Thread.currentThread().getName() + " say Hi as well!"));
        one.setPriority(Thread.MAX_PRIORITY);
        one.start();
    }
}
