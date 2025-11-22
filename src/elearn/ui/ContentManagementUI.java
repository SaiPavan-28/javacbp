package elearn.ui;

import elearn.DBConnection;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ContentManagementUI extends Frame {

    private TextField tfTitle, tfDesc, tfPath, tfDeleteId;
    private TextArea taOutput;

    public ContentManagementUI() {
        super("Content Management");

        setSize(600, 450);
        setLayout(new BorderLayout());

        Panel top = new Panel(new GridLayout(4, 2));

        top.add(new Label("Content ID (Delete):"));
        tfDeleteId = new TextField();
        top.add(tfDeleteId);

        top.add(new Label("Title:"));
        tfTitle = new TextField();
        top.add(tfTitle);

        top.add(new Label("Description:"));
        tfDesc = new TextField();
        top.add(tfDesc);

        top.add(new Label("File Path:"));
        tfPath = new TextField();
        top.add(tfPath);

        add(top, BorderLayout.NORTH);

        // Buttons
        Panel panelButtons = new Panel(new FlowLayout());

        Button btnAdd = new Button("Upload Content");
        Button btnList = new Button("List Content");
        Button btnDelete = new Button("Delete Content");

        panelButtons.add(btnAdd);
        panelButtons.add(btnList);
        panelButtons.add(btnDelete);

        add(panelButtons, BorderLayout.CENTER);

        // Output area
        taOutput = new TextArea();
        add(taOutput, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> uploadContent());
        btnList.addActionListener(e -> listContent());
        btnDelete.addActionListener(e -> deleteContent());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });

        setVisible(true);
    }

    private void uploadContent() {
        String title = tfTitle.getText();
        String desc = tfDesc.getText();
        String path = tfPath.getText();

        if (title.isEmpty() || desc.isEmpty() || path.isEmpty()) {
            showMsg("All fields required!");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO contents (title, description, file_path) VALUES (?, ?, ?)");

            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, path);

            ps.executeUpdate();
            conn.close();

            showMsg("Content Uploaded Successfully!");

        } catch (Exception e) {
            showMsg("Error: " + e.getMessage());
        }
    }

    private void listContent() {
        taOutput.setText("");

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM contents");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                taOutput.append(
                        "ID: " + rs.getInt("id") + "\n" +
                        "Title: " + rs.getString("title") + "\n" +
                        "Description: " + rs.getString("description") + "\n" +
                        "File: " + rs.getString("file_path") + "\n" +
                        "-----------------------------\n"
                );
            }
            conn.close();

        } catch (Exception e) {
            showMsg("Error: " + e.getMessage());
        }
    }

    private void deleteContent() {
        String id = tfDeleteId.getText();

        if (id.isEmpty()) {
            showMsg("Enter Content ID!");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM contents WHERE id = ?");
            ps.setInt(1, Integer.parseInt(id));

            int rows = ps.executeUpdate();
            conn.close();

            if (rows > 0)
                showMsg("Content Deleted!");
            else
                showMsg("ID Not Found!");

        } catch (Exception e) {
            showMsg("Error: " + e.getMessage());
        }
    }

    private void showMsg(String msg) {
        Dialog d = new Dialog(this, "Message", true);
        d.setLayout(new FlowLayout());
        d.add(new Label(msg));
        Button b = new Button("OK");
        b.addActionListener(a -> d.dispose());
        d.add(b);
        d.setSize(300, 150);
        d.setVisible(true);
    }
}
