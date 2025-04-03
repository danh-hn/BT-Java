import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import java.awt.*;
import java.io.File;

public class Main {
    private static final String FILE_NAME = "students.xml";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createGUI);
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Quản lý Sinh viên XML");
        frame.setSize(450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel labelID = new JLabel("ID:");
        JTextField textID = new JTextField(5);
        JLabel labelName = new JLabel("Tên:");
        JTextField textName = new JTextField(10);
        JLabel labelAge = new JLabel("Tuổi:");
        JTextField textAge = new JTextField(5);

        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnShow = new JButton("Hiển thị");

        JTextArea textArea = new JTextArea(15, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.add(labelID); frame.add(textID);
        frame.add(labelName); frame.add(textName);
        frame.add(labelAge); frame.add(textAge);
        frame.add(btnAdd); frame.add(btnDelete); frame.add(btnShow);
        frame.add(scrollPane);

        btnAdd.addActionListener(e -> {
            String id = textID.getText();
            String name = textName.getText();
            String age = textAge.getText();
            if (!id.isEmpty() && !name.isEmpty() && !age.isEmpty()) {
                addStudent(id, name, age);
                textID.setText(""); textName.setText(""); textAge.setText("");
                JOptionPane.showMessageDialog(frame, "Thêm sinh viên thành công!");
            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng nhập đủ thông tin!");
            }
        });

        btnDelete.addActionListener(e -> {
            String id = textID.getText();
            if (!id.isEmpty()) {
                if (deleteStudent(id)) {
                    JOptionPane.showMessageDialog(frame, "Xóa thành công!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Không tìm thấy sinh viên!");
                }
                textID.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Nhập ID để xóa!");
            }
        });

        btnShow.addActionListener(e -> textArea.setText(readXML()));

        frame.setVisible(true);
    }

    private static String readXML() {
        StringBuilder result = new StringBuilder();
        try {
            File xmlFile = new File(FILE_NAME);
            if (!xmlFile.exists()) return "File XML chưa tồn tại.";

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("student");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getAttribute("id");
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String age = element.getElementsByTagName("age").item(0).getTextContent();
                    result.append("ID: ").append(id).append(" | Tên: ").append(name).append(" | Tuổi: ").append(age).append("\n");
                }
            }
        } catch (Exception e) {
            return "Lỗi đọc file XML!";
        }
        return result.toString();
    }

    private static void addStudent(String id, String name, String age) {
        try {
            File xmlFile = new File(FILE_NAME);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;

            if (!xmlFile.exists()) {
                doc = builder.newDocument();
                Element root = doc.createElement("students");
                doc.appendChild(root);
            } else {
                doc = builder.parse(xmlFile);
            }

            if (id.isEmpty() == id.isEmpty()) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Id đã tồn tai!");
                return;
                
            }
            Element root = doc.getDocumentElement();
            Element student = doc.createElement("student");
            student.setAttribute("id", id);

            Element nameElement = doc.createElement("name");
            nameElement.setTextContent(name);
            Element ageElement = doc.createElement("age");
            ageElement.setTextContent(age);

            student.appendChild(nameElement);
            student.appendChild(ageElement);
            root.appendChild(student);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
            if (id.isEmpty() == id.isEmpty()) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Id đã tồn tai!");
                return;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteStudent(String id) {
        try {
            File xmlFile = new File(FILE_NAME);
            if (!xmlFile.exists()) return false;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            NodeList nodeList = doc.getElementsByTagName("student");
            boolean found = false;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("id").equals(id)) {
                        element.getParentNode().removeChild(element);
                        found = true;
                        break;
                    }
                }
            }

            if (!found) return false;

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
