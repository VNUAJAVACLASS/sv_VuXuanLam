package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.ConnectDB;
import model.Book;

public class BookDAO {

    private Connection getConnection() throws SQLException {
        return ConnectDB.getConnection();
    }

    public List<Book> getAllBook() {
        String sql = "SELECT id, title, content, price FROM book ORDER BY id DESC";
        List<Book> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getLong("price")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Book findById(int id) {
        String sql = "SELECT id, title, content, price FROM book WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getLong("price")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO book (title, content, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getContent());
            ps.setLong(3, book.getPrice());
            int rows = ps.executeUpdate();
            if (rows > 0) { try (ResultSet keys = ps.getGeneratedKeys()) { return keys.next(); } }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(Book book) {
        String sql = "UPDATE book SET title=?, content=?, price=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getContent());
            ps.setLong(3, book.getPrice());
            ps.setInt(4, book.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public long getPriceById(int id) {
        String sql = "SELECT price FROM book WHERE id=?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getLong(1); }
        } catch (Exception e) { e.printStackTrace(); }
        return 0L;
    }
    public boolean updatePrice(int id, long price) {
        String sql = "UPDATE book SET price=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, price);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
