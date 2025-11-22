package elearn.dao;

import elearn.models.Course;
import elearn.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Add a new course
    public void addCourse(Course c) {
        String sql = "INSERT INTO courses (title, description, instructor, source) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getTitle());
            ps.setString(2, c.getDescription());
            ps.setString(3, c.getInstructor());
            ps.setString(4, c.getSource());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    // List all courses (formatted)
    public String listCourses() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM courses ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sb.append("-------------------------------\n");
                sb.append("Course ID : ").append(rs.getInt("id")).append("\n");
                sb.append("Title     : ").append(rs.getString("title")).append("\n");
                sb.append("Description: ").append(rs.getString("description")).append("\n");
                sb.append("Instructor: ").append(rs.getString("instructor")).append("\n");
                sb.append("Video     : ").append(rs.getString("source")).append("\n");
                sb.append("-------------------------------\n\n");
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

        if (sb.length() == 0)
            return "No courses available.";

        return sb.toString();
    }

    // Get a single course by ID
    public Course getCourseById(int courseId) {
        Course c = null;

        String sql = "SELECT * FROM courses WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Course();
                c.setId(rs.getInt("id"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setInstructor(rs.getString("instructor"));
                c.setSource(rs.getString("source"));  // FIXED COLUMN
            }

        } catch (Exception e) {
            System.out.println("Error fetching course: " + e.getMessage());
        }

        return c;
    }

    // Delete course
    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error deleting course: " + e.getMessage());
        }
    }
}
