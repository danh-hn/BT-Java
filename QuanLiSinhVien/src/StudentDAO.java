import java.sql.*;

public class StudentDAO {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=universityms;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "your_password";

    public Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Không thể kết nối đến cơ sở dữ liệu: " + e.getMessage());
            return null;
        }
    }

    private String generateEmail(String name, int studentID) {
        String[] parts = name.toLowerCase().split(" ");
        StringBuilder email = new StringBuilder();

        for (int i = 0; i < parts.length - 1; i++) {
            email.append(parts[i].charAt(0));
        }
        email.append(parts[parts.length - 1]);
        email.append(studentID);
        email.append("@vku.udn.vn");

        return email.toString();
    }

    public void addStudent(String name, int age, double gpa) {
        String query = "INSERT INTO Student (Name, Age, Email, GPA) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return;
            }

            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, "");
            stmt.setDouble(4, gpa);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                int studentID = rs.getInt(1);
                String email = generateEmail(name, studentID);

                String updateQuery = "UPDATE Student SET Email = ? WHERE StudentID = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, email);
                    updateStmt.setInt(2, studentID);
                    updateStmt.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Lỗi khi cập nhật email: " + e.getMessage());
                }
            } else {
                System.err.println("Không thể lấy được ID sinh viên mới.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sinh viên: " + e.getMessage());
        }
    }

    public boolean login(String email, String password) {
        String query = "SELECT * FROM Student WHERE Email = ? AND Password = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                return false;
            }

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Đăng nhập thành công cho sinh viên: " + rs.getString("Name"));
                return true;
            } else {
                System.out.println("Đăng nhập thất bại! Sai email hoặc mật khẩu.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi đăng nhập: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        System.out.println("Đăng xuất thành công.");
    }
}
