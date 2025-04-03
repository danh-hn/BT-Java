package Bai2;
import java.io.*;

public class docDuLieuBanPhim {
    public static void main(String[] args) {

        String fileName = "output.txt";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            System.out.println("Nhập dữ liệu (Nhập 'exit' để thoát):");
            String line;

            while (!(line = reader.readLine()).equalsIgnoreCase("exit")) {
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Dữ liệu đã được lưu vào " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
