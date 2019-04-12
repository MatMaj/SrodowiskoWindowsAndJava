import java.util.concurrent.atomic.AtomicInteger;

public class ProduceAndConsume implements Runnable {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final Integer LIMIT = 100;
    private Item[] items;
    private int i;

    public ProduceAndConsume(Item[] items){ this.items = items; }

    public void run() {
        while ((i = COUNTER.getAndIncrement()) < LIMIT) {
            //System.out.println("ProduceAndConsume - " + i);
            items[i].produceMe();
            items[i].consumeMe();
        }
    }
}
