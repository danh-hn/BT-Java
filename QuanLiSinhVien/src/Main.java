import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();

// Thêm sinh viên mới
        studentDAO.addStudent("Nguyen Van A", 20, 3.8);
        boolean isLoggedIn = studentDAO.login("nguyenvana123@vku.udn.vn", "123");

        // Lấy danh sách sinh viên
        List<Student> students = studentDAO.getAllStudents();
        System.out.println("Danh sách sinh viên:");
        for (Student s : students) {
            System.out.println(s.getStudentID() + " - " + s.getName() + " - " + s.getEmail());
        }

        // Cập nhật sinh viên
        if (!students.isEmpty()) {
            Student s = students.get(0);
            s.setGpa(3.8);
            studentDAO.updateStudent(s);
        }

        // Xóa sinh viên (xóa ID 1)
        studentDAO.deleteStudent(1);
    }
}
