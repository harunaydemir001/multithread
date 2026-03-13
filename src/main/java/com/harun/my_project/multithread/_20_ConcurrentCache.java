package com.harun.my_project.multithread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class _20_ConcurrentCache {
    private static final Map<String, String> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            new Thread(() -> {
                String key = "Key @ " + threadNum;
                for (int j = 0; j < 3; j++) {
                    String value = getCachedValue(key);
                    System.out.println("Thread: " + Thread.currentThread().getName() + ": Key = " + key + ", value = " + value);
                }
            }).start();
        }
    }

    private static String getCachedValue(final String key) {
        String value = cache.get(key);
        if (value == null) {
            value = compute(key);
            cache.put(key, value);
            System.out.println("add cache: " + key);
        }
        return value;
    }

    private static String compute(final String key) {
        System.out.println(key + " not present int the cache, so going to compute!");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
}
