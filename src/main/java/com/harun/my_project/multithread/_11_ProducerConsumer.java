package com.harun.my_project.multithread;

import java.util.ArrayList;
import java.util.List;

public class _11_ProducerConsumer {
    public static void main(String[] args) {

        Worker worker = new Worker(5, 0);

        Thread t1 = new Thread(() -> {
            try {
                worker.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                worker.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
    }
}

class Worker {
    private int sequence = 0;
    private final Integer top;
    private final Integer bottom;
    private final List<Integer> container = new ArrayList<>();
    private final Object lock = new Object();

    Worker(Integer top, Integer bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (container.size() == top) {
                    System.out.println("Container full, waiting for items to be removed... ");
                    lock.wait();
                } else {
                    System.out.println(sequence + " Added to the container");
                    container.add(sequence++);
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (container.size() == bottom) {
                    System.out.println("Container empty, waiting for items to be added ... ");
                    lock.wait();
                } else {
                    System.out.println(container.removeFirst() + " removed from the container");
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }
}
