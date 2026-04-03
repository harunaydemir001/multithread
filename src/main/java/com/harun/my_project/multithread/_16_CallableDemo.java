package com.harun.my_project.multithread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class _16_CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            Future<Integer> result = executorService.submit(new ReturnValueTask());

//            result.isDone();
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

class PriceCalculationDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Callable<Integer> productPriceTask = () -> {
            Thread.sleep(2000);
            System.out.println("Product price calculated.");
            return 100;
        };

        Callable<Integer> shippingTask = () -> {
            Thread.sleep(3000);
            System.out.println("Shipping calculated");
            return 20;
        };

        Callable<Integer> discountTask = () -> {
            Thread.sleep(1000);
            System.out.println("Discount calculated");
            return -15;
        };

        List<Callable<Integer>> tasks = Arrays.asList(
                productPriceTask,
                shippingTask,
                discountTask
        );

        try {
            List<Future<Integer>> results = executor.invokeAll(tasks);

            int total = 0;

            for (Future<Integer> future : results) {
                try {
                    total += future.get(4, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                   throw new RuntimeException(e);
                }
            }

            System.out.println("Total price: " + total);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        System.out.println("Main thread is over!");
    }
}