package Bai8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.*;

public class Bai8 {
    private static final String KEY = "danh";

    public static void main(String[] args) {
        String[] files = { "file1.txt", "file2.txt" };
        ExecutorService executor = Executors.newFixedThreadPool(files.length);
        ConcurrentHashMap<String, Integer> ketQuaTimKiem = new ConcurrentHashMap<>();

        for (String file : files) {
            executor.execute(() -> {
                int count = timTuKhoaTrongTep(file, KEY);
                ketQuaTimKiem.put(file, count);
            });
        }

        executor.shutdown();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ketQuaTimKiem.forEach((file, soLan) -> {
            System.out.println("Từ khóa '" + KEY + "' xuất hiện " + soLan + " lần trong tệp " + file);
        });
    }

    private static int timTuKhoaTrongTep(String fileName, String KEY) {
        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                counter += demSoLanXuatHien(line, KEY);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc tệp: " + fileName);
        }
        return counter;
    }

    private static int demSoLanXuatHien(String line, String KEY) {
        int count = 0;
        int index = 0;
        while ((index = line.indexOf(KEY, index)) != -1) {
            count++;
            index += KEY.length();
        }
        return count;
    }
}
