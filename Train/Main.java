package Train;

public class Main {
    public static void main(String[] args) {
        TicketCounter counter = new TicketCounter();

        for (int i = 1; i <= 15; i++) {
            new Passenger(counter, "Hành khách " + i).start();
        }
    }
}
