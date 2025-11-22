package elearn.ui;

import elearn.DBConnection;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;

public class StudentDownloadUI extends Frame {

    private int courseId;
    private TextArea taList;
    private TextField tfId;

    public StudentDownloadUI(int courseId) {
        super("Download Course Materials");
        this.courseId = courseId;

        setSize(600, 450);
        setLayout(new BorderLayout());

        Label header = new Label("Course Materials", Label.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        add(header, BorderLayout.NORTH);

        taList = new TextArea();
        add(taList, BorderLayout.CENTER);

        Panel bottom = new Panel(new FlowLayout());
        bottom.add(new Label("Enter Content ID to Open:"));

        tfId = new TextField(10);
        bottom.add(tfId);

        Button btnOpen = new Button("Open File");
        bottom.add(btnOpen);

        add(bottom, BorderLayout.SOUTH);

        btnOpen.addActionListener(e -> openFile());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });

        loadContentList();
        setVisible(true);
    }

    private void loadContentList() {
        taList.setText("");

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT id, title, description FROM contents WHERE course_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                taList.append(
                    "ID: " + rs.getInt("id") + "\n" +
                    "Title: " + rs.getString("title") + "\n" +
                    "Description: " + rs.getString("description") + "\n" +
                    "-------------------------------\n"
                );
            }

            conn.close();

        } catch (Exception e) {
            taList.setText("Error loading content: " + e.getMessage());
        }
    }

    private void openFile() {
        try {
            int id = Integer.parseInt(tfId.getText().trim());
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT file_path FROM contents WHERE id = ? AND course_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, courseId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String path = rs.getString("file_path");

                File file = new File(path);
                if (!file.exists()) {
                    showDialog("File not found: " + path);
                    return;
                }

                Desktop.getDesktop().open(file);

            } else {
                showDialog("Invalid content ID");
            }

            conn.close();

        } catch (Exception e) {
            showDialog("Error: " + e.getMessage());
        }
    }

    private void showDialog(String msg) {
        Dialog d = new Dialog(this, "Message", true);
        d.setLayout(new FlowLayout());
        d.add(new Label(msg));
        Button ok = new Button("OK");
        ok.addActionListener(a -> d.dispose());
        d.add(ok);
        d.setSize(350, 150);
        d.setVisible(true);
    }
}
