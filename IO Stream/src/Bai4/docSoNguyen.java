package Bai4;
import java.io.*;

public class docSoNguyen {
    public static void main(String[] args) {
        String fileName = "numbers.txt";
        int[] numbers = {10, 20, 30, 40, 50};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < numbers.length; i++) {
                writer.write(Integer.toString(numbers[i]));
                writer.newLine();
            }
            System.out.println("Dữ liệu đã được ghi vào tệp " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            System.out.println("Dữ liệu đọc từ tệp:");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
