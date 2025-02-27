package Customer;

public class Producer extends Thread {
    Store store;
    long index = 1;

    public Producer(Store store) {
        this.store = store;
    }

    public void run() {
        while (true) {
            store.put(index);
            index++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
