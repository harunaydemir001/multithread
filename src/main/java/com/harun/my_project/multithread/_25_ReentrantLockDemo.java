package com.harun.my_project.multithread;

import java.util.concurrent.locks.ReentrantLock;

public class _25_ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int sharedData = 0;

    public void methodA() {
        lock.lock();
        try {
            //Cricital session
            sharedData++;
            System.out.println("Method A: sharedData = " + sharedData);
            //Call methodB, which also requires the lock
            methodB();
        } finally {
            lock.unlock();
        }
    }

    public void methodB() {
        lock.lock();
        try {
            //Cricital session
            sharedData--;
            System.out.println("Method B: sharedData = " + sharedData);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        _25_ReentrantLockDemo example = new _25_ReentrantLockDemo();
        //Create and start multiple thread
        for (int i = 0; i < 5; i++) {
            new Thread(example::methodA).start();
        }
    }
}