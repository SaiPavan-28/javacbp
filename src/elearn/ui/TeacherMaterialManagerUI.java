package elearn.ui;

import elearn.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;

public class TeacherMaterialManagerUI extends JFrame {
    private JComboBox<String> cbCourses;
    private JTextArea taList;
    private JTextField tfId, tfTitle, tfDesc, tfPath;
    private JButton btnLoad, btnBrowse, btnUpdate, btnDelete;

    public TeacherMaterialManagerUI(){
        super("Manage Materials");
        init();
        setSize(820,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        getContentPane().setBackground(new Color(18,18,18));
        JPanel root=new JPanel(new BorderLayout(8,8));
        root.setBackground(new Color(18,18,18));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(root);

        JPanel top=new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(new Color(18,18,18));
        cbCourses=new JComboBox<>(); top.add(new JLabel("Course:")); top.add(cbCourses);
        btnLoad=new JButton("Load Materials"); btnLoad.addActionListener(e->loadMaterials());
        style(btnLoad); top.add(btnLoad);
        root.add(top, BorderLayout.NORTH);

        taList=new JTextArea(); taList.setEditable(false); taList.setBackground(new Color(28,28,28)); taList.setForeground(Color.WHITE);
        root.add(new JScrollPane(taList), BorderLayout.CENTER);

        JPanel form=new JPanel(new GridLayout(5,2,8,8));
        form.setBackground(new Color(18,18,18));
        tfId=new JTextField(); tfTitle=new JTextField(); tfDesc=new JTextField(); tfPath=new JTextField();
        form.add(new JLabel("Content ID:")); form.add(tfId);
        form.add(new JLabel("Title:")); form.add(tfTitle);
        form.add(new JLabel("Description:")); form.add(tfDesc);
        form.add(new JLabel("File Path/Link:")); form.add(tfPath);

        btnBrowse=new JButton("Browse"); btnBrowse.addActionListener(e->browse());
        btnUpdate=new JButton("Update"); btnUpdate.addActionListener(e->updateMaterial());
        btnDelete=new JButton("Delete"); btnDelete.addActionListener(e->deleteMaterial());

        JPanel btns=new JPanel(new FlowLayout(FlowLayout.LEFT)); btns.setBackground(new Color(18,18,18));
        style(btnBrowse); style(btnUpdate); style(btnDelete);
        btns.add(btnBrowse); btns.add(btnUpdate); btns.add(btnDelete);

        JPanel south=new JPanel(new BorderLayout());
        south.setBackground(new Color(18,18,18));
        south.add(form, BorderLayout.CENTER); south.add(btns, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        loadCourses();
    }

    private void style(JButton b){ b.setBackground(new Color(12,110,200)); b.setForeground(Color.WHITE); }
    private void loadCourses(){
        cbCourses.removeAllItems();
        try (Connection conn = DBConnection.getConnection();
             Statement st=conn.createStatement();
             ResultSet rs=st.executeQuery("SELECT id, title FROM courses ORDER BY id")){
            while(rs.next()) cbCourses.addItem(rs.getInt("id")+":"+rs.getString("title"));
        } catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }

    private void loadMaterials(){
        taList.setText("");
        if(cbCourses.getItemCount()==0) return;
        int courseId = Integer.parseInt(((String)cbCourses.getSelectedItem()).split(":")[0]);
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, title, description, file_path FROM contents WHERE course_id = ? ORDER BY id")){
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                taList.append("ID: "+rs.getInt("id")+"\nTitle: "+rs.getString("title")+"\nDesc: "+rs.getString("description")+"\nPath: "+rs.getString("file_path")+"\n--------------------------------\n");
            }
        } catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }

    private void browse(){
        JFileChooser fc=new JFileChooser();
        int res=fc.showOpenDialog(this);
        if(res==JFileChooser.APPROVE_OPTION) tfPath.setText(fc.getSelectedFile().getAbsolutePath());
    }

    private void updateMaterial(){
        try {
            int id=Integer.parseInt(tfId.getText().trim());
            try (Connection conn=DBConnection.getConnection();
                 PreparedStatement ps=conn.prepareStatement("UPDATE contents SET title=?, description=?, file_path=? WHERE id=?")){
                ps.setString(1, tfTitle.getText().trim());
                ps.setString(2, tfDesc.getText().trim());
                ps.setString(3, tfPath.getText().trim());
                ps.setInt(4, id);
                int r=ps.executeUpdate(); JOptionPane.showMessageDialog(this, r>0?"Updated":"Not found"); loadMaterials();
            }
        } catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }

    private void deleteMaterial(){
        try {
            int id=Integer.parseInt(tfId.getText().trim());
            try (Connection conn=DBConnection.getConnection();
                 PreparedStatement ps=conn.prepareStatement("DELETE FROM contents WHERE id = ?")){
                ps.setInt(1,id); int r=ps.executeUpdate(); JOptionPane.showMessageDialog(this, r>0?"Deleted":"Not found"); loadMaterials();
            }
        } catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }
}
