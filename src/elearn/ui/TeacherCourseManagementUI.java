package elearn.ui;

import elearn.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TeacherCourseManagementUI extends JFrame {
    private JTextField tfTitle, tfDesc, tfInstr, tfSource;
    private JTextArea taList;

    public TeacherCourseManagementUI(){
        super("Teacher Course Management");
        init();
        setSize(800,520);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        getContentPane().setBackground(new Color(18,18,18));
        JPanel root=new JPanel(new BorderLayout(10,10));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        root.setBackground(new Color(18,18,18));
        add(root);

        JPanel form=new JPanel(new GridLayout(4,2,8,8));
        form.setBackground(new Color(18,18,18));
        form.add(new JLabel("Title:")); tfTitle=new JTextField(); tfTitle.setBackground(new Color(40,40,40)); tfTitle.setForeground(Color.WHITE); form.add(tfTitle);
        form.add(new JLabel("Description:")); tfDesc=new JTextField(); tfDesc.setBackground(new Color(40,40,40)); tfDesc.setForeground(Color.WHITE); form.add(tfDesc);
        form.add(new JLabel("Instructor:")); tfInstr=new JTextField(); tfInstr.setBackground(new Color(40,40,40)); tfInstr.setForeground(Color.WHITE); form.add(tfInstr);
        form.add(new JLabel("Lecture Source URL:")); tfSource=new JTextField(); tfSource.setBackground(new Color(40,40,40)); tfSource.setForeground(Color.WHITE); form.add(tfSource);

        root.add(form, BorderLayout.NORTH);

        JPanel btns=new JPanel(new FlowLayout(FlowLayout.LEFT));
        btns.setBackground(new Color(18,18,18));
        JButton add=new JButton("Add Course"); add.addActionListener(e->addCourse());
        JButton list=new JButton("List Courses"); list.addActionListener(e->listCourses());
        btns.add(add); btns.add(list);
        root.add(btns, BorderLayout.CENTER);

        taList=new JTextArea(); taList.setEditable(false); taList.setBackground(new Color(28,28,28)); taList.setForeground(Color.WHITE);
        root.add(new JScrollPane(taList), BorderLayout.SOUTH);
    }

    private void addCourse(){
        String t=tfTitle.getText().trim(), d=tfDesc.getText().trim(), i=tfInstr.getText().trim(), s=tfSource.getText().trim();
        if(t.isEmpty()){ JOptionPane.showMessageDialog(this,"Title required"); return; }
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement ps=conn.prepareStatement("INSERT INTO courses (title, description, instructor, source) VALUES (?, ?, ?, ?)")){
            ps.setString(1,t); ps.setString(2,d); ps.setString(3,i); ps.setString(4,s); ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Course added"); tfTitle.setText(""); tfDesc.setText(""); tfInstr.setText(""); tfSource.setText("");
        } catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }

    private void listCourses(){
        taList.setText("");
        try (Connection conn=DBConnection.getConnection();
             Statement st=conn.createStatement();
             ResultSet rs=st.executeQuery("SELECT id, title, instructor, source FROM courses ORDER BY id")){
            while(rs.next()) taList.append("ID: "+rs.getInt("id")+" | "+rs.getString("title")+" | Instructor: "+rs.getString("instructor")+" | Video: "+rs.getString("source")+"\n");
        } catch(Exception e){ taList.setText("Error: "+e.getMessage()); }
    }
}
