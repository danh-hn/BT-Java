package Bai7;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bai7 {
    private static final String input = "input.txt";
    private static final String output = "output.txt";
    private static final int size = 1024 * 1024;

    public static void main(String[] args) {
        File tepNguon = new File(input);
        long kichThuocTep = tepNguon.length();
        int soLuongPhan = (int) Math.ceil((double) kichThuocTep / size);
        ExecutorService executor = Executors.newFixedThreadPool(soLuongPhan);
        for (int i = 0; i < soLuongPhan; i++) {
            final int chiMucPhan = i;
            executor.execute(() -> docVaGhiPhan(chiMucPhan, kichThuocTep));
        }
        executor.shutdown();

        System.out.println("Đọc tệp hoàn tất");
    }

    private static void docVaGhiPhan(int chiMucPhan, long kichThuocTep) {
        try (RandomAccessFile tep = new RandomAccessFile(input, "r")) {
            long viTriBatDau = (long) chiMucPhan * size;
            tep.seek(viTriBatDau);
            byte[] boDem = new byte[size];
            int soByteDoc = tep.read(boDem);
            synchronized (Bai7.class) {
                try (FileOutputStream fos = new FileOutputStream(output, true)) {
                    fos.write(boDem, 0, soByteDoc);
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc phần " + chiMucPhan);
        }
    }
}