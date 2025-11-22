package elearn.ui;

import elearn.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserManagementUI extends JFrame {

    private JTextField tfUser, tfPass, tfRole, tfDelete;
    private JTextArea taList;

    public UserManagementUI() {
        super("User Management");
        init();
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {

        getContentPane().setBackground(new Color(20, 20, 20));
        setLayout(new BorderLayout(10, 10));

        // ---------- TOP FORM ----------
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(20, 20, 20));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);
        gc.anchor = GridBagConstraints.WEST;

        gc.gridx = 0; gc.gridy = 0;
        form.add(label("Username:"), gc);
        gc.gridx = 1;
        tfUser = field(20);
        form.add(tfUser, gc);

        gc.gridx = 0; gc.gridy++;
        form.add(label("Password:"), gc);
        gc.gridx = 1;
        tfPass = field(20);
        form.add(tfPass, gc);

        gc.gridx = 0; gc.gridy++;
        form.add(label("Role:"), gc);
        gc.gridx = 1;
        tfRole = field(20);
        form.add(tfRole, gc);

        gc.gridx = 0; gc.gridy++;
        form.add(label("Delete ID:"), gc);
        gc.gridx = 1;
        tfDelete = field(10);
        form.add(tfDelete, gc);

        add(form, BorderLayout.NORTH);


        // ---------- CENTER LARGE LIST PANEL ----------
        taList = new JTextArea();
        taList.setEditable(false);
        taList.setForeground(Color.WHITE);
        taList.setBackground(new Color(30, 30, 30));
        taList.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(taList);
        scroll.setPreferredSize(new Dimension(500, 300));  // <-- BIGGER LIST AREA
        add(scroll, BorderLayout.CENTER);


        // ---------- BUTTONS ----------
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setBackground(new Color(20, 20, 20));

        JButton addBtn = button("Add User");
        JButton listBtn = button("List Users");
        JButton delBtn = button("Delete User");

        btnPanel.add(addBtn);
        btnPanel.add(listBtn);
        btnPanel.add(delBtn);

        add(btnPanel, BorderLayout.SOUTH);


        // ---------- EVENTS ----------
        addBtn.addActionListener(e -> addUser());
        listBtn.addActionListener(e -> listUsers());
        delBtn.addActionListener(e -> deleteUser());
    }

    // -------------------- HELPER FUNCTIONS -----------------------

    private JLabel label(String s) {
        JLabel l = new JLabel(s);
        l.setForeground(Color.WHITE);
        return l;
    }

    private JTextField field(int size) {
        JTextField f = new JTextField(size);
        f.setBackground(new Color(40, 40, 40));
        f.setForeground(Color.WHITE);
        return f;
    }

    private JButton button(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(30, 144, 255));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    // -------------------- CRUD FUNCTIONS -----------------------

    private void addUser() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users (username,password,role) VALUES (?,?,?)"
            );
            ps.setString(1, tfUser.getText());
            ps.setString(2, tfPass.getText());
            ps.setString(3, tfRole.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "User added successfully!");
            tfUser.setText(""); tfPass.setText(""); tfRole.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void listUsers() {
        taList.setText("");

        try {
            Connection conn = DBConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users ORDER BY id");

            while (rs.next()) {
                taList.append("ID: " + rs.getInt("id") +
                        " | " + rs.getString("username") +
                        " | " + rs.getString("role") + "\n");
            }

        } catch (Exception ex) {
            taList.setText("Error: " + ex.getMessage());
        }
    }

    private void deleteUser() {
        try {
            int id = Integer.parseInt(tfDelete.getText());
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            JOptionPane.showMessageDialog(this, rows > 0 ? "User deleted!" : "User not found!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
