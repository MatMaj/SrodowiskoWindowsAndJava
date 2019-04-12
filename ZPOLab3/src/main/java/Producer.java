import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final Integer LIMIT = 100;
    private Item[] items;
    private int i;

    public Producer(Item[] items){ this.items = items; }

    public void run() {
        while ((i = COUNTER.getAndIncrement()) < LIMIT) {
            //System.out.println("Producer - " + i);
            items[i].produceMe();
        }
    }
}
