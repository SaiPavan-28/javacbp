package elearn.ui;

import elearn.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;

public class ContentUploadUI extends JFrame {
    private JComboBox<String> cbCourses;
    private JTextField tfTitle, tfDesc, tfPath;
    private JButton btnBrowse, btnUpload;

    public ContentUploadUI(){
        super("Upload Content");
        init();
        setSize(620,320);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        getContentPane().setBackground(new Color(20,20,20));
        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBackground(new Color(20,20,20));
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        add(root);

        JPanel form = new JPanel(new GridLayout(4,2,8,8));
        form.setBackground(new Color(20,20,20));
        form.add(label("Select Course:")); cbCourses = new JComboBox<>(); form.add(cbCourses);
        form.add(label("Title:")); tfTitle = field(30); form.add(tfTitle);
        form.add(label("Description:")); tfDesc = field(30); form.add(tfDesc);
        form.add(label("File Path or Link:")); tfPath = field(30); form.add(tfPath);
        root.add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(new Color(20,20,20));
        btnBrowse = new JButton("Browse File"); btnBrowse.addActionListener(e->browse());
        btnUpload = new JButton("Upload"); btnUpload.addActionListener(e->upload());
        style(btnBrowse); style(btnUpload);
        bottom.add(btnBrowse); bottom.add(btnUpload);
        root.add(bottom, BorderLayout.SOUTH);

        loadCourses();
    }

    private JLabel label(String s){ JLabel l=new JLabel(s); l.setForeground(Color.LIGHT_GRAY); return l; }
    private JTextField field(int c){ JTextField f=new JTextField(c); f.setBackground(new Color(40,40,40)); f.setForeground(Color.WHITE); return f; }
    private void style(JButton b){ b.setBackground(new Color(12,120,210)); b.setForeground(Color.WHITE); }

    private void loadCourses(){
        cbCourses.removeAllItems();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, title FROM courses ORDER BY id")){
            while(rs.next()) cbCourses.addItem(rs.getInt("id")+":"+rs.getString("title"));
        } catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }

    private void browse(){
        JFileChooser fc=new JFileChooser();
        int res = fc.showOpenDialog(this);
        if(res==JFileChooser.APPROVE_OPTION) tfPath.setText(fc.getSelectedFile().getAbsolutePath());
    }

    private void upload(){
        if(cbCourses.getItemCount()==0){ JOptionPane.showMessageDialog(this,"No courses"); return; }
        String sel=(String)cbCourses.getSelectedItem();
        int courseId=Integer.parseInt(sel.split(":")[0]);
        String title=tfTitle.getText().trim(), desc=tfDesc.getText().trim(), path=tfPath.getText().trim();
        if(title.isEmpty() || path.isEmpty()){ JOptionPane.showMessageDialog(this,"Title and path required"); return; }
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO contents (title, description, file_path, course_id) VALUES (?, ?, ?, ?)")){
            ps.setString(1,title); ps.setString(2,desc); ps.setString(3,path); ps.setInt(4,courseId);
            ps.executeUpdate(); JOptionPane.showMessageDialog(this,"Uploaded"); tfTitle.setText(""); tfDesc.setText(""); tfPath.setText("");
        } catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }
}
