import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        final int NUMBER_OF_ITEMS = 100;
        final int NUMBER_OF_THREADS = 7;
        final int NUMBER_OF_PRODUCERS = 4;
        final int NUMBER_OF_CONSUMERS = 3;

        long startTime = 0;
        long endTime = 0;

        Item[] items = new Item[NUMBER_OF_ITEMS];
        for (int i = 0; i < NUMBER_OF_ITEMS; i++) {
            items[i] = new Item();
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("1 - zwyczajne, 2 - thread pool");
        switch (sc.nextInt()) {
            case 1:
                ArrayList<Thread> threads = new ArrayList<Thread>(NUMBER_OF_CONSUMERS + NUMBER_OF_PRODUCERS);

                for (int i = 0; i < NUMBER_OF_PRODUCERS; i++) {
                    Thread t = new Thread(new Producer(items));
                    threads.add(t);
                }

                for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
                    Thread t = new Thread(new Consumer(items));
                    threads.add(t);
                }

                startTime = System.nanoTime();
                for (Thread thread : threads){
                    thread.start();
                }
                for (Thread thread : threads){
                    thread.join();
                }
                endTime = System.nanoTime();
                break;
            case 2:
                startTime = System.nanoTime();
                ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);
                for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                    executor.submit(new ProduceAndConsume(items));
                }
                executor.shutdown();
                boolean finished = executor.awaitTermination(5, TimeUnit.MINUTES);
                endTime = System.nanoTime();
                break;
        }
        System.out.println("Czas: " + ((endTime - startTime) / 1000000) + "ms" );
    }
}
