import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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

        System.out.println("1 - zwyczajne, 2 - ThreadPoolExecutor, 3 - stream api, 4 - ExecutorService");
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
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);
                for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                    threadPoolExecutor.submit(new ProduceAndConsume(items));
                }
                threadPoolExecutor.shutdown();
                boolean finished = threadPoolExecutor.awaitTermination(5, TimeUnit.MINUTES);
                endTime = System.nanoTime();
                break;
            case 3:
                startTime = System.nanoTime();
                Stream.of(items).parallel().forEach(item -> {
                    item.produceMe();
                    item.consumeMe();
                });
                endTime = System.nanoTime();
                break;
            case 4:
                startTime = System.nanoTime();
                ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
                for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                    executorService.submit(new ProduceAndConsume(items));
                }
                executorService.shutdown();
                while (!executorService.isTerminated()) {
                }
                endTime = System.nanoTime();
                break;

        }
        System.out.println("Czas: " + ((endTime - startTime) / 1000000) + "ms" );
    }
}
