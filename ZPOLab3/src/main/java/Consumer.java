import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final Integer LIMIT = 100;
    private Item[] items;
    private int i;

    public Consumer(Item[] items){ this.items = items; }

    public void run() {
        while ((i = COUNTER.getAndIncrement()) < LIMIT) {
            //System.out.println("Consumer - " + i);
            items[i].consumeMe();
        }
    }
}
