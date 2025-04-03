import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=universityms;encrypt=true;trustServerCertificate=true";
    private static final String USER = "danh";
    private static final String PASSWORD = "123";

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        connect();
    }
}
