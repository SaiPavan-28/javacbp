package elearn.ui;

import javax.swing.*;
import java.awt.*;

public class StudentUI extends JFrame {
    public StudentUI(String username){
        super("Student Dashboard - " + username);
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

        JLabel header=new JLabel("Welcome Student", SwingConstants.LEFT);
        header.setForeground(Color.WHITE); header.setFont(new Font("Segoe UI", Font.BOLD,22));
        root.add(header, BorderLayout.NORTH);

        JPanel buttons=new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.setBackground(new Color(22,22,22));
        JButton view=new JButton("View Courses"); view.addActionListener(e-> new StudentCoursesUI());
        JButton logout=new JButton("Logout"); logout.addActionListener(e->{ dispose(); new LoginUI(); });
        style(view); style(logout);
        buttons.add(view); buttons.add(logout);
        root.add(buttons, BorderLayout.CENTER);
    }

    private void style(JButton b){ b.setBackground(new Color(10,110,200)); b.setForeground(Color.WHITE); b.setFont(new Font("Segoe UI", Font.BOLD,14)); }
}
