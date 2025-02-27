package Train;
public class TicketCounter {
    private int tickets = 10;

    public synchronized void buyTicket(String buyer) {
        if (tickets > 0) {
            System.out.println(buyer + " mua vé số " + tickets);
            tickets--; 
        } else {
            System.out.println(buyer + " đến trễ, vé đã hết!");
        }
    }
}
