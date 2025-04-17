import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncryptionApp extends JFrame {
    private JComboBox<String> algorithmSelector;
    private JTextArea inputText;
    private JTextArea outputText;
    private JButton encryptDecryptButton;

    public EncryptionApp() {
        setTitle("Encrypt/Decrypt App (AES & RSA)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout());
        algorithmSelector = new JComboBox<>(new String[]{"AES", "RSA"});
        topPanel.add(new JLabel("Chọn thuật toán:"));
        topPanel.add(algorithmSelector);

        // Center panel
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputText = new JTextArea("Nhập dữ liệu cần mã hoá...");
        outputText = new JTextArea("Kết quả sẽ hiển thị ở đây...");
        outputText.setEditable(false);
        centerPanel.add(new JScrollPane(inputText));
        centerPanel.add(new JScrollPane(outputText));

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        encryptDecryptButton = new JButton("Mã hoá & Giải mã (đa luồng)");
        bottomPanel.add(encryptDecryptButton);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action listener
        encryptDecryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runEncryptionDecryption();
            }
        });

        setVisible(true);
    }

    private void runEncryptionDecryption() {
        String algorithm = (String) algorithmSelector.getSelectedItem();
        String text = inputText.getText();

        outputText.setText("Đang xử lý...");

        new Thread(() -> {
            try {
                Encryptable encryptor = algorithm.equals("AES") ? new AESEncryption() : new RSAEncryption();
                String encrypted = encryptor.encrypt(text);

                SwingUtilities.invokeLater(() -> outputText.append("\n[Mã hoá] " + encrypted));

                String decrypted = encryptor.decrypt(encrypted);

                SwingUtilities.invokeLater(() -> outputText.append("\n[Giải mã] " + decrypted));
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> outputText.setText("Lỗi: " + ex.getMessage()));
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EncryptionApp());
    }
}
