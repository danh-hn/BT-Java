package Train;

public class Passenger extends Thread {
    private TicketCounter counter;
    private String name;

    public Passenger(TicketCounter counter, String name) {
        this.counter = counter;
        this.name = name;
    }

    @Override
    public void run() {
        counter.buyTicket(name);
    }
}
