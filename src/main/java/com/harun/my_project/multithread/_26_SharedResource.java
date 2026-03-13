package com.harun.my_project.multithread;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class _26_SharedResource {
    private int counter = 0;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void increment() {
        lock.writeLock().lock();
        try {
            counter++;
            System.out.println(Thread.currentThread().getName() + " writes : " + counter);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void getValue() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reads : " + counter);
        } finally {
            lock.readLock().unlock();
        }
    }
}

class ReadWriteLockDemo {
    public static void main(String[] args) {
        _26_SharedResource sr = new _26_SharedResource();
        for (int i = 0; i < 2; i++) {
            Thread readerThread = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    sr.getValue();
                }
            });
            readerThread.setName("Reader Thread " + (i + 1));
            readerThread.start();
        }

        Thread writerThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                sr.increment();
            }
        });
        writerThread.setName("Writer Thread");
        writerThread.start();
    }
}