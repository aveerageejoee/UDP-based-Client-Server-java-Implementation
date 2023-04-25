package Worksheets;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public void compute(int i) {
        try {
            // "simulate" computation
            Thread.sleep(100);
            System.out.println(" " + i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Main p = new Main();

        // Create a pool of 10 worker threads
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Submit 1000 tasks to the worker threads
        for (int i = 0; i <= 1000; i++)  {
            int finalI = i;
            executor.submit(() -> p.compute(finalI));
        }
        AtomicInteger x = new AtomicInteger(0);



        // Shut down the executor when all tasks are completed
        executor.shutdown();
    }
}


