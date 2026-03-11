package com.harun.my_project.multithread;


public class _06_DeamonUserThreadDemo {
    public static void main(String[] args) {
        Thread deamonThread = new Thread(new DaemonThreadHelper());
        Thread userThread = new Thread(new UserThreadHelper());
        deamonThread.setDaemon(true);

        deamonThread.start();
        userThread.start();
    }
}

class DaemonThreadHelper implements Runnable {
    @Override
    public void run() {
        int count = 0;
        while (count < 500) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
            System.out.println("DaemonHelper running");
        }
    }
}

class UserThreadHelper implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User thread done with execution");
    }
}
