package com.harun.my_project.multithread;

public class _01_SequentialExecution {

    public static void main(String[] args) {
        demo1();
        demo2();
    }

    private static void demo1() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("From demo 1 " + i);
        }
    }

    private static void demo2() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("From demo 2 " + i);
        }
    }
}
