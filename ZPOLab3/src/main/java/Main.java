import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String args[]) {
        final int NUMBER_OF_ITEMS = 100;
        final int NUMBER_OF_THREADS = 8;
        final int NUMBER_OF_PRODUCERS = 4;
        final int NUMBER_OF_CONSUMERS = 3;

        Item[] items = new Item[NUMBER_OF_ITEMS];
        for (int i = 0; i < NUMBER_OF_ITEMS; i++) {
            items[i] = new Item();
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("1 - zwyczajne, 2 - thread pool");
        switch (sc.nextInt()) {
            case 1:
                for (int i = 0; i < NUMBER_OF_PRODUCERS; i++) {
                    Thread t = new Thread(new Producer(items));
                    t.start();
                }

                for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
                    Thread t = new Thread(new Consumer(items));
                    t.start();
                }
                break;
            case 2:
                ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);
                for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                    executor.submit(new ProduceAndConsume(items));
                }
                break;
        }
    }
}
