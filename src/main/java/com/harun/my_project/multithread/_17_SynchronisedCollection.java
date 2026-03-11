package com.harun.my_project.multithread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _17_SynchronisedCollection {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();


        System.out.println(list.size());
    }
}
