import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {
    CardLayout layout;
    JPanel container;
    JPanel loginPanel, registerPanel;

    public Main() {
        setTitle("Đăng nhập & Đăng ký");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        container = new JPanel(layout);

        loginPanel = createLoginPanel();
        registerPanel = createRegisterPanel();

        container.add(loginPanel, "login");
        container.add(registerPanel, "register");

        add(container);
        layout.show(container, "login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Đăng nhập");
        JButton switchToRegister = new JButton("Chưa có tài khoản?");

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            try (Connection conn = Database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("SELECT password_hash FROM users WHERE username = ?");
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String hash = rs.getString("password_hash");
                    if (HashUtils.verify(password, hash)) {
                        JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Sai mật khẩu.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Người dùng không tồn tại.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        switchToRegister.addActionListener(e -> layout.show(container, "register"));

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(loginBtn);
        panel.add(switchToRegister);
        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton registerBtn = new JButton("Đăng ký");
        JButton switchToLogin = new JButton("Đã có tài khoản?");

        registerBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String hash = HashUtils.hash(password);
            try (Connection conn = Database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password_hash) VALUES (?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, hash);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
                layout.show(container, "login");
            } catch (SQLIntegrityConstraintViolationException dup) {
                JOptionPane.showMessageDialog(this, "Username đã tồn tại.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        switchToLogin.addActionListener(e -> layout.show(container, "login"));

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(registerBtn);
        panel.add(switchToLogin);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
