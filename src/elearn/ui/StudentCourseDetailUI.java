package elearn.ui;

import elearn.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class StudentCourseDetailUI extends JFrame {
    private int courseId;
    private JTextArea taInfo;
    private JTextField tfMaterialId;

    public StudentCourseDetailUI(int courseId){
        super("Course Details");
        this.courseId = courseId;
        init();
        setSize(880,640);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        getContentPane().setBackground(new Color(18,18,18));
        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBackground(new Color(18,18,18));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(root);

        taInfo = new JTextArea();
        taInfo.setEditable(false);
        taInfo.setBackground(new Color(28,28,28)); taInfo.setForeground(Color.WHITE);
        taInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        root.add(new JScrollPane(taInfo), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(new Color(18,18,18));
        JButton watch = new JButton("Watch Lecture Video"); watch.addActionListener(e->watchVideo());
        style(watch);
        bottom.add(watch);

        bottom.add(new JLabel("Material ID:")); ((JLabel)bottom.getComponent(bottom.getComponentCount()-1)).setForeground(Color.LIGHT_GRAY);
        tfMaterialId = new JTextField(8); tfMaterialId.setBackground(new Color(40,40,40)); tfMaterialId.setForeground(Color.WHITE);
        bottom.add(tfMaterialId);

        JButton open = new JButton("Open Material"); open.addActionListener(e->openMaterial()); style(open);
        JButton download = new JButton("Download Material"); download.addActionListener(e->downloadMaterial()); style(download);
        JButton refresh = new JButton("Refresh"); refresh.addActionListener(e->loadMaterials()); style(refresh);

        bottom.add(open); bottom.add(download); bottom.add(refresh);
        root.add(bottom, BorderLayout.SOUTH);

        loadCourseInfo();
        loadMaterials();
    }

    private void style(JButton b){ b.setBackground(new Color(10,110,200)); b.setForeground(Color.WHITE); }

    private void loadCourseInfo(){
        taInfo.setText("");
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement ps=conn.prepareStatement("SELECT * FROM courses WHERE id = ?")){
            ps.setInt(1, courseId);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                taInfo.append("Course ID: "+rs.getInt("id")+"\n");
                taInfo.append("Title: "+rs.getString("title")+"\n");
                taInfo.append("Description: "+rs.getString("description")+"\n");
                taInfo.append("Instructor: "+rs.getString("instructor")+"\n\n");
                taInfo.append("Lecture Video (course-level):\n"+rs.getString("source")+"\n\n");
            }
        } catch(Exception e){ taInfo.append("Error: "+e.getMessage()+"\n"); }
    }

    private void loadMaterials(){
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement ps=conn.prepareStatement("SELECT id, title, description, file_path FROM contents WHERE course_id = ? ORDER BY id")){
            ps.setInt(1, courseId);
            ResultSet rs=ps.executeQuery();
            taInfo.append("Study Materials:\n\n");
            while(rs.next()){
                taInfo.append("ID: "+rs.getInt("id")+"\n");
                taInfo.append("Title: "+rs.getString("title")+"\n");
                taInfo.append("Description: "+rs.getString("description")+"\n");
                taInfo.append("Path/Link: "+rs.getString("file_path")+"\n");
                taInfo.append("-----------------------------------------------\n");
            }
        } catch(Exception e){ taInfo.append("Error loading materials: "+e.getMessage()+"\n"); }
    }

    private void watchVideo(){
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement ps=conn.prepareStatement("SELECT source FROM courses WHERE id = ?")){
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String s = rs.getString("source");
                if(s!=null && s.startsWith("http")) Desktop.getDesktop().browse(new URI(s));
                else JOptionPane.showMessageDialog(this,"No valid video link.");
            }
        } catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }

    private void openMaterial(){
        try {
            int id = Integer.parseInt(tfMaterialId.getText().trim());
            try (Connection conn=DBConnection.getConnection();
                 PreparedStatement ps=conn.prepareStatement("SELECT file_path FROM contents WHERE id = ?")){
                ps.setInt(1, id);
                ResultSet rs=ps.executeQuery();
                if(!rs.next()){ JOptionPane.showMessageDialog(this,"Invalid ID"); return; }
                String path = rs.getString("file_path");
                if(path==null){ JOptionPane.showMessageDialog(this,"No path"); return; }
                if(path.startsWith("http")) Desktop.getDesktop().browse(new URI(path));
                else Desktop.getDesktop().open(new File(path));
            }
        } catch(NumberFormatException nfe){ JOptionPane.showMessageDialog(this,"Enter Material ID"); }
        catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }

    private void downloadMaterial(){
        try {
            int id = Integer.parseInt(tfMaterialId.getText().trim());
            try (Connection conn=DBConnection.getConnection();
                 PreparedStatement ps=conn.prepareStatement("SELECT file_path FROM contents WHERE id = ?")){
                ps.setInt(1, id);
                ResultSet rs=ps.executeQuery();
                if(!rs.next()){ JOptionPane.showMessageDialog(this,"Invalid ID"); return; }
                String path = rs.getString("file_path");
                if(path.startsWith("http")){ JOptionPane.showMessageDialog(this,"Online link — cannot download directly."); return; }
                File source = new File(path);
                if(!source.exists()){ JOptionPane.showMessageDialog(this,"File not found: "+path); return; }
                JFileChooser chooser = new JFileChooser();
                chooser.setSelectedFile(new File(source.getName()));
                int res = chooser.showSaveDialog(this);
                if(res==JFileChooser.APPROVE_OPTION){
                    File dest = chooser.getSelectedFile();
                    Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(this,"Downloaded to: "+dest.getAbsolutePath());
                }
            }
        } catch(NumberFormatException nfe){ JOptionPane.showMessageDialog(this,"Enter Material ID"); }
        catch(Exception e){ JOptionPane.showMessageDialog(this,"Error: "+e.getMessage()); }
    }
}
