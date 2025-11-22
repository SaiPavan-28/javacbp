package elearn.dao;

import elearn.DBConnection;
import elearn.models.Content;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContentDAO {

    public boolean addContent(Content c) {
        String sql = "INSERT INTO contents (title, description, file_path) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getTitle());
            ps.setString(2, c.getDescription());
            ps.setString(3, c.getFilePath());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<Content> getAllContents() {
        List<Content> out = new ArrayList<>();
        String sql = "SELECT id, title, description, file_path FROM contents ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Content(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("file_path")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    public String getFilePathById(int id) {
        String sql = "SELECT file_path FROM contents WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("file_path");
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean deleteContent(int id) {
        String sql = "DELETE FROM contents WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
