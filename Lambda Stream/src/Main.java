import java.util.*;
import java.util.stream.*;

class Student {
    String name;
    int score;

    Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

   @Override
   public String toString() {
       return name + " - " + score;
   }
}

public class Main {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("An", 7),
                new Student("Bình", 4),
                new Student("Chi", 8),
                new Student("Dũng", 5),
                new Student("Hà", 3)
        );

        // Bài 1: Tìm học sinh điểm cao nhất
        System.out.println("Bài 1");
        students.stream()
                .max(Comparator.comparingInt(s -> s.score))
                .ifPresent(s -> System.out.println("Học sinh điểm cao nhất: " + s.name + " - " + s.score));

        // Bài 2: Tính điểm trung bình hệ 10
        System.out.println("\n== Bài 2 ==");
        double avg = students.stream()
                .mapToInt(s -> s.score)
                .average()
                .orElse(0);
        System.out.printf("Điểm trung bình cả lớp: %.1f\n", avg);

        System.out.println("\nBài 3");
        Map<String, List<Student>> result = students.stream()
                .collect(Collectors.groupingBy(s -> s.score >= 5 ? "Pass" : "Fail"));

        result.forEach((key, list) -> {
            System.out.println(key + ":");
            list.forEach(System.out::println);
        });
    }
}
