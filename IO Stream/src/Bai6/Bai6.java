package Bai6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bai6 {
    private static final String OUTPUT_FILE = "output.txt";
    private static final Object lock = new Object();

    public static void main(String[] args) {
        String[] files = { "file1.txt", "file2.txt" };
        ExecutorService executor = Executors.newFixedThreadPool(files.length);
        for (String file : files) {
            executor.execute(() -> docVaGhiTep(file));
        }
        executor.shutdown();

        System.out.println("Hoàn Thành");
    }

    private static void docVaGhiTep(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            synchronized (lock) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
                    writer.write(content.toString());
                    writer.write("\n");
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc tệp: " + file);
        }
    }
}