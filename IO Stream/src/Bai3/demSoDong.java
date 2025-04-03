package Bai3;
import java.io.*;

public class demSoDong {
    public static void main(String[] args) {
        String fileName = "demdong.txt";
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
            }
            System.out.println("Số dòng trong tệp là: " + count);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
