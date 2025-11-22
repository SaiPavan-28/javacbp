package elearn.ui;

import elearn.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginUI extends JFrame {
    private JTextField tfUser;
    private JPasswordField pfPass;
    private JButton btnLogin;

    public LoginUI() {
        super("E-Learning — Login");
        initLook();
        initComponents();
        setSize(420,230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initLook() {
        getContentPane().setBackground(new Color(34,34,34));
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout(12,12));
        root.setBackground(new Color(34,34,34));
        root.setBorder(BorderFactory.createEmptyBorder(14,14,14,14));
        add(root);

        JLabel title = new JLabel("E-Learning Login", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(34,34,34));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.anchor = GridBagConstraints.WEST;

        gc.gridx = 0; gc.gridy = 0;
        JLabel l1 = new JLabel("Username:");
        l1.setForeground(Color.LIGHT_GRAY);
        form.add(l1, gc);

        gc.gridx = 1;
        tfUser = new JTextField(18);
        styleField(tfUser);
        form.add(tfUser, gc);

        gc.gridx = 0; gc.gridy++;
        JLabel l2 = new JLabel("Password:");
        l2.setForeground(Color.LIGHT_GRAY);
        form.add(l2, gc);

        gc.gridx = 1;
        pfPass = new JPasswordField(18);
        styleField(pfPass);
        form.add(pfPass, gc);

        root.add(form, BorderLayout.CENTER);

        btnLogin = new JButton("Login");
        styleButton(btnLogin);
        btnLogin.addActionListener(e -> attemptLogin());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(new Color(34,34,34));
        bottom.add(btnLogin);
        root.add(bottom, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnLogin);
    }

    private void styleField(JComponent c){
        c.setBackground(new Color(48,48,48));
        c.setForeground(Color.WHITE);
        c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        if(c instanceof JTextField) ((JTextField)c).setBorder(BorderFactory.createLineBorder(new Color(80,80,80)));
    }

    private void styleButton(JButton b){
        b.setBackground(new Color(10,132,255));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void attemptLogin() {
        String user = tfUser.getText().trim();
        String pass = new String(pfPass.getPassword()).trim();
        if(user.isEmpty() || pass.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter username and password");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT role FROM users WHERE username = ? AND password = ?")) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role").toUpperCase();
                dispose();
                SwingUtilities.invokeLater(() -> {
                    switch (role) {
                        case "ADMIN": new AdminUI(user); break;
                        case "TEACHER": new TeacherUI(user); break;
                        case "STUDENT": new StudentUI(user); break;
                        default: JOptionPane.showMessageDialog(null,"Unknown role: "+role);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this,"Invalid username or password");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"DB Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new LoginUI());
    }
}
