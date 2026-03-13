package com.harun.my_project.multithread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _24_ConditionDemo {
    private final Integer MAX_SIZE = 5;
    private final Lock lock = new ReentrantLock();
    private final Queue<Integer> buffer = new LinkedList<>();
    private final Condition bufferNotFull = lock.newCondition();
    private final Condition bufferNotEmpty = lock.newCondition();


    private void produce(int item) throws InterruptedException {
        lock.lock();
        //processing
        try {
            while (buffer.size() == MAX_SIZE) {
                bufferNotFull.await();
            }
            buffer.offer(item);
            System.out.println("Produced >> " + item);
            bufferNotEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    private void consume() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.isEmpty()) {
                bufferNotEmpty.await();
            }
            System.out.println("Consumed << " + buffer.poll());
            bufferNotFull.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        _24_ConditionDemo a24ConditionDemo = new _24_ConditionDemo();

        Thread producerThread = new Thread(() -> {
            try {

                for (int i = 0; i < 10; i++) {
                    a24ConditionDemo.produce(i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    a24ConditionDemo.consume();
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producerThread.start();
        consumerThread.start();
    }
}
