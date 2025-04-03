import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadWebContent {
    public static void main(String[] args) {
        // Danh sách các URL cần tải
        List<String> urls = List.of(
                "https://jsonplaceholder.typicode.com/posts/1",
                "https://jsonplaceholder.typicode.com/posts/2",
                "https://jsonplaceholder.typicode.com/posts/3",
                "https://jsonplaceholder.typicode.com/posts/4",
                "https://jsonplaceholder.typicode.com/posts/5"
        );

        // Sử dụng ThreadPool với 5 luồng để tải dữ liệu song song
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < urls.size(); i++) {
            int fileIndex = i + 1;
            String url = urls.get(i);
            executor.execute(() -> downloadAndSave(url, "output_" + fileIndex + ".txt"));
        }


        // Đóng ExecutorService sau khi hoàn tất
        executor.shutdown();
    }

    private static void downloadAndSave(String urlString, String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlString).openStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            System.out.println("✅ Tải xong: " + urlString + " -> " + fileName);

        } catch (IOException e) {
            System.err.println("❌ Lỗi tải từ " + urlString + ": " + e.getMessage());
        }
    }
}
