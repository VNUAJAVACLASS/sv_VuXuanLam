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


    public int countAllBooks() {
        String sql = "SELECT COUNT(*) FROM book";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đếm tổng sách theo từ khóa (search)
    public int countBooksByTitle(String keyword) {
        String sql = "SELECT COUNT(*) FROM book WHERE title LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // =========================
    // 2. LẤY SÁCH THEO TRANG
    // =========================
    /**
     * Lấy sách phân trang
     * @param page      trang hiện tại (bắt đầu từ 1)
     * @param pageSize  số sách / trang
     */
    public List<Book> getBooksPage(int page, int pageSize) {
        String sql = "SELECT id, title, content, price " +
                     "FROM book " +
                     "ORDER BY id DESC " +
                     "LIMIT ? OFFSET ?";

        List<Book> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pageSize);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getLong("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tìm sách theo title + phân trang
     */
    public List<Book> searchByTitlePage(String keyword, int page, int pageSize) {
        String sql = "SELECT id, title, content, price " +
                     "FROM book " +
                     "WHERE title LIKE ? " +
                     "ORDER BY id DESC " +
                     "LIMIT ? OFFSET ?";

        List<Book> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getLong("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================
    // 3. CÁC HÀM CŨ (VẪN GIỮ)
    // =========================

    // Lấy toàn bộ sách (admin dùng, nếu muốn bỏ phân trang)
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tìm theo ID
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm sách
    public boolean addBook(Book book) {
        String sql = "INSERT INTO book (title, content, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getContent());
            ps.setLong(3, book.getPrice());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    return keys.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật sách
    public boolean update(Book book) {
        String sql = "UPDATE book SET title = ?, content = ?, price = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getContent());
            ps.setLong(3, book.getPrice());
            ps.setInt(4, book.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xoá sách
    public boolean delete(int id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy giá theo ID
    public long getPriceById(int id) {
        String sql = "SELECT price FROM book WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    // Cập nhật giá
    public boolean updatePrice(int id, long price) {
        String sql = "UPDATE book SET price = ? WHERE id = ?";
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

    // Lấy sách mới nhất (dựa trên id DESC), giới hạn số lượng
    public List<Book> findNewest(int limit) {
        String sql = "SELECT id, title, content, price FROM book ORDER BY id DESC LIMIT ?";
        List<Book> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getLong("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy sách "top" (ví dụ sắp xếp theo giá giảm dần)
    public List<Book> findTopBooks(int limit) {
        String sql = "SELECT id, title, content, price FROM book ORDER BY price DESC LIMIT ?";
        List<Book> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getLong("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tìm sách theo tiêu đề (bản không phân trang – vẫn giữ nếu anh dùng chỗ khác)
    public List<Book> searchByTitle(String keyword) {
        String sql = "SELECT id, title, content, price FROM book WHERE title LIKE ? ORDER BY id DESC";
        List<Book> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getLong("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
