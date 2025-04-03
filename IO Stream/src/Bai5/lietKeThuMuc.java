package Bai5;

import java.io.*;

public class lietKeThuMuc {
    public static void main(String[] args) {
        File thumuc = new File(".");

        File[] danhsach = thumuc.listFiles();

        if (danhsach != null) {
            System.out.println("Danh sách các tệp trong thư mục:");
            for (int i = 0; i < danhsach.length; i++) {
                if (danhsach[i].isFile()) {
                    System.out.println(danhsach[i].getName());
                }
            }
        } else {
            System.out.println("Không thể truy cập thư mục.");
        }
    }
}

