package Customer;

import java.util.LinkedList;
import java.util.List;

public class Store {
    private List<Long> store = new LinkedList<>();
    private final int size = 5;

    // Sản xuất sản phẩm
    public synchronized boolean put(long index) {
        while (store.size() >= size) {
            try {
                System.out.println("Kho đầy! Thợ sản xuất chờ...");
                wait(); // Chờ nếu kho đầy
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        store.add(index);
        System.out.println("Sản phẩm " + index + " đã được sản xuất.");
        notify(); // Báo hiệu cho Customer.Consumer có thể lấy hàng
        return true;
    }

    // Tiêu thụ sản phẩm
    public synchronized long get() {
        while (store.isEmpty()) {
            try {
                System.out.println("Kho trống! Người tiêu dùng chờ...");
                wait(); // Chờ nếu kho trống
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long product = store.remove(0);
        System.out.println("Người tiêu dùng đã lấy sản phẩm " + product);
        notify(); // Báo hiệu cho Customer.Producer có thể sản xuất thêm
        return product;
    }
}