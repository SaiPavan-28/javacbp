package elearn.ui;

import javax.swing.*;
import java.awt.*;

public class TeacherUI extends JFrame {
    public TeacherUI(String username){
        super("Teacher Dashboard - " + username);
        init();
        setSize(900,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        getContentPane().setBackground(new Color(22,22,22));
        JPanel root=new JPanel(new BorderLayout(12,12));
        root.setBackground(new Color(22,22,22));
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        add(root);

        JLabel header=new JLabel("Teacher Dashboard", SwingConstants.LEFT);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        root.add(header, BorderLayout.NORTH);

        JPanel buttons=new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        buttons.setBackground(new Color(22,22,22));
        JButton upload=new JButton("Upload Content"); upload.addActionListener(e-> new ContentUploadUI());
        JButton manage=new JButton("Manage Materials"); manage.addActionListener(e-> new TeacherMaterialManagerUI());
        JButton courses=new JButton("Manage My Courses"); courses.addActionListener(e-> new TeacherCourseManagementUI());
        JButton logout=new JButton("Logout"); logout.addActionListener(e->{ dispose(); new LoginUI(); });

        styleButton(upload); styleButton(manage); styleButton(courses); styleButton(logout);
        buttons.add(upload); buttons.add(manage); buttons.add(courses); buttons.add(logout);
        root.add(buttons, BorderLayout.CENTER);
    }

    private void styleButton(JButton b){ b.setBackground(new Color(10,120,210)); b.setForeground(Color.WHITE); b.setFont(new Font("Segoe UI", Font.BOLD,14)); }
}
