package elearn.ui;

import javax.swing.*;
import java.awt.*;

public class AdminUI extends JFrame {
    public AdminUI(String username){
        super("Admin Dashboard - " + username);
        init();
        setSize(900,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        getContentPane().setBackground(new Color(24,24,24));
        JPanel root = new JPanel(new BorderLayout(12,12));
        root.setBackground(new Color(24,24,24));
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        add(root);

        JLabel header = new JLabel("Admin Dashboard", SwingConstants.LEFT);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2,2,12,12));
        center.setBackground(new Color(24,24,24));

        JButton btnCourses = new JButton("Manage Courses");
        btnCourses.addActionListener(e -> new CourseManagementUI());
        styleBig(btnCourses);

        JButton btnUsers = new JButton("Manage Users");
        btnUsers.addActionListener(e -> new UserManagementUI());
        styleBig(btnUsers);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> { dispose(); new LoginUI(); });
        styleBig(btnLogout);

        JButton btnPlaceholder = new JButton("Reports");
        styleBig(btnPlaceholder);

        center.add(btnCourses); center.add(btnUsers);
        center.add(btnPlaceholder); center.add(btnLogout);
        root.add(center, BorderLayout.CENTER);
    }

    private void styleBig(JButton b){
        b.setBackground(new Color(20,120,210));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setFocusPainted(false);
    }
}
