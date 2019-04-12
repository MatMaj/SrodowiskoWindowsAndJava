import java.util.concurrent.atomic.AtomicInteger;

public class Item {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private final String name;
    private volatile boolean produced = false;
    private volatile boolean consumed = false;
    public Item() {
        this.name = "Item-" + COUNTER.getAndIncrement();
    }
    public synchronized void produceMe() {
        if (produced) {
            throw new RuntimeException(name + " already produced");
        }
        if (consumed) {
            throw new RuntimeException(name + " already consumed");
        }
        System.out.println("Producing: " + name);
        delay(2);
        produced = true;
        System.out.println("Produced: " + name);
    }
    public synchronized void consumeMe() {
        if (!produced) {
            throw new RuntimeException(name + " not produced yet");
        }
        if (consumed) {
            throw new RuntimeException(name + " already consumed");
        }
        System.out.println("Consuming: " + name);
        delay(3);
        consumed = true;
        System.out.println("Consumed: " + name);
    }
    private void delay(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
