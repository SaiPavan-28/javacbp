package elearn.ui;

import elearn.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentCoursesUI extends JFrame {
    private JList<String> list;
    private DefaultListModel<String> model;

    public StudentCoursesUI(){
        super("Available Courses");
        init();
        setSize(760,520);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        getContentPane().setBackground(new Color(20,20,20));
        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBackground(new Color(20,20,20));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(root);

        JLabel header = new JLabel("Select a Course", SwingConstants.CENTER);
        header.setForeground(Color.WHITE); header.setFont(new Font("Segoe UI", Font.BOLD,18));
        root.add(header, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setBackground(new Color(30,30,30)); list.setForeground(Color.WHITE);
        JScrollPane sp = new JScrollPane(list);
        root.add(sp, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(new Color(20,20,20));
        JButton open = new JButton("Open Course Details"); open.addActionListener(e->openSelected());
        JButton refresh = new JButton("Refresh"); refresh.addActionListener(e->loadCourses());
        style(open); style(refresh);
        bottom.add(open); bottom.add(refresh);
        root.add(bottom, BorderLayout.SOUTH);

        loadCourses();
    }

    private void style(JButton b){ b.setBackground(new Color(10,110,200)); b.setForeground(Color.WHITE); }
    private void loadCourses(){
        model.clear();
        try (Connection conn=DBConnection.getConnection();
             Statement st=conn.createStatement();
             ResultSet rs=st.executeQuery("SELECT id, title FROM courses ORDER BY id")){
            while(rs.next()) model.addElement(rs.getInt("id")+": "+rs.getString("title"));
        } catch(Exception e){ model.addElement("Error: "+e.getMessage()); }
    }

    private void openSelected(){
        String sel = list.getSelectedValue();
        if(sel==null){ JOptionPane.showMessageDialog(this,"Select a course"); return; }
        int id = Integer.parseInt(sel.split(":")[0]);
        new StudentCourseDetailUI(id);
    }
}
