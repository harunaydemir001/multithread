package com.harun.my_project.multithread;

import java.util.concurrent.*;

public class _16_CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            Future<Integer> result = executorService.submit(new ReturnValueTask());

//            result.isDone();
//            result.isCancelled();
//            result.get();

            System.out.println(result.get(6, TimeUnit.SECONDS));
            System.out.println("Main thread execution complated!");
        }
    }
}

class ReturnValueTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(5000);
        return 12;
    }
}