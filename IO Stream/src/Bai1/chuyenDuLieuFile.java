package Bai1;

import java.io.*;

public class chuyenDuLieuFile {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("text.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("new.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Sao chép tệp văn bản thành công!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


