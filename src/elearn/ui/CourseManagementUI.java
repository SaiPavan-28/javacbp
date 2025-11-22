package elearn.ui;

import elearn.dao.CourseDAO;
import elearn.models.Course;

import java.awt.*;
import java.awt.event.*;

public class CourseManagementUI extends Frame {

    private TextField tfTitle, tfDescription, tfInstructor, tfSource, tfDeleteId;
    private TextArea taList;
    private CourseDAO dao = new CourseDAO();

    public CourseManagementUI() {
        super("Course Management");

        setSize(900, 650);
        setLayout(null);
        setBackground(new Color(20, 20, 20));   // dark theme background

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);

        // TITLE LABEL
        Label lblTitle = new Label("Title:");
        lblTitle.setBounds(50, 80, 120, 25);
        lblTitle.setFont(labelFont);
        lblTitle.setForeground(Color.WHITE);
        add(lblTitle);

        tfTitle = new TextField();
        tfTitle.setBounds(200, 80, 300, 25);
        tfTitle.setFont(textFont);
        add(tfTitle);

        // DESCRIPTION LABEL
        Label lblDesc = new Label("Description:");
        lblDesc.setBounds(50, 120, 120, 25);
        lblDesc.setFont(labelFont);
        lblDesc.setForeground(Color.WHITE);
        add(lblDesc);

        tfDescription = new TextField();
        tfDescription.setBounds(200, 120, 300, 25);
        tfDescription.setFont(textFont);
        add(tfDescription);

        // INSTRUCTOR LABEL
        Label lblInst = new Label("Instructor:");
        lblInst.setBounds(50, 160, 120, 25);
        lblInst.setFont(labelFont);
        lblInst.setForeground(Color.WHITE);
        add(lblInst);

        tfInstructor = new TextField();
        tfInstructor.setBounds(200, 160, 300, 25);
        tfInstructor.setFont(textFont);
        add(tfInstructor);

        // LECTURE SOURCE LABEL
        Label lblSrc = new Label("Lecture Source (URL):");
        lblSrc.setBounds(50, 200, 150, 25);
        lblSrc.setFont(labelFont);
        lblSrc.setForeground(Color.WHITE);
        add(lblSrc);

        tfSource = new TextField();
        tfSource.setBounds(200, 200, 300, 25);
        tfSource.setFont(textFont);
        add(tfSource);

        // DELETE ID LABEL
        Label lblDel = new Label("Delete Course ID:");
        lblDel.setBounds(50, 240, 150, 25);
        lblDel.setFont(labelFont);
        lblDel.setForeground(Color.WHITE);
        add(lblDel);

        tfDeleteId = new TextField();
        tfDeleteId.setBounds(200, 240, 100, 25);
        tfDeleteId.setFont(textFont);
        add(tfDeleteId);

        // BUTTONS
        Button btnAdd = createButton("Add Course", 50, 290);
        Button btnList = createButton("List Courses", 200, 290);
        Button btnDelete = createButton("Delete Course", 350, 290);

        add(btnAdd);
        add(btnList);
        add(btnDelete);

        // TEXT AREA FOR LIST
        taList = new TextArea("", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
        taList.setBounds(50, 340, 800, 260);
        taList.setFont(new Font("Consolas", Font.PLAIN, 14));
        taList.setEditable(false);
        add(taList);

        // ADD BUTTON ACTION
        btnAdd.addActionListener(e -> {
            Course c = new Course();
            c.setTitle(tfTitle.getText());
            c.setDescription(tfDescription.getText());
            c.setInstructor(tfInstructor.getText());
            c.setSource(tfSource.getText());

            dao.addCourse(c);

            showMessage("Course Added Successfully!");
            clearFields();
        });

        // LIST BUTTON ACTION
        btnList.addActionListener(e -> {
            String data = dao.listCourses();
            taList.setText(data);
        });

        // DELETE BUTTON ACTION
        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfDeleteId.getText());
                dao.deleteCourse(id);
                showMessage("Course Deleted Successfully!");
            } catch (Exception ex) {
                showMessage("Invalid Course ID!");
            }
        });

        // WINDOW CLOSE
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });

        setVisible(true);
    }

    private Button createButton(String text, int x, int y) {
        Button b = new Button(text);
        b.setBounds(x, y, 120, 30);
        b.setBackground(new Color(30, 144, 255));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return b;
    }

    private void showMessage(String msg) {
        Dialog d = new Dialog(this, "Message", true);
        d.setLayout(new FlowLayout());
        d.setSize(300, 120);
        d.setLocationRelativeTo(this);
        Label l = new Label(msg);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        d.add(l);

        Button ok = new Button("OK");
        ok.addActionListener(e -> d.dispose());
        d.add(ok);

        d.setVisible(true);
    }

    private void clearFields() {
        tfTitle.setText("");
        tfDescription.setText("");
        tfInstructor.setText("");
        tfSource.setText("");
        tfDeleteId.setText("");
    }
}
