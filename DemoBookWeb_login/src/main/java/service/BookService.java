package service;

import java.util.List;

import dao.BookDAO;
import model.Book;

public class BookService {
    private final BookDAO dao;

    public BookService() {
        this(new BookDAO());
    }

    // tiện cho test/DI nếu cần
    public BookService(BookDAO dao) {
        this.dao = dao;
    }

    /* ========= READ ========= */
    public List<Book> getAllBook() {
        return dao.getAllBook();
    }

    public Book findById(int id) {
        if (id <= 0) return null;
        return dao.findById(id);
    }

    public long getPriceById(int id) {
        if (id <= 0) return 0L;
        return dao.getPriceById(id);
    }

    /* ========= CREATE / UPDATE ========= */
    public boolean addBook(Book book) {
        if (book == null) return false;
        // sanitize nhẹ
        String title = book.getTitle() == null ? "" : book.getTitle().trim();
        String content = book.getContent() == null ? "" : book.getContent().trim();
        long price = Math.max(0, book.getPrice()); // không cho âm

        book.setTitle(title);
        book.setContent(content);
        book.setPrice(price);

        return dao.addBook(book);
    }

    public boolean updateBook(Book book) {
        if (book == null || book.getId() <= 0) return false;
        String title = book.getTitle() == null ? "" : book.getTitle().trim();
        String content = book.getContent() == null ? "" : book.getContent().trim();
        long price = Math.max(0, book.getPrice());

        book.setTitle(title);
        book.setContent(content);
        book.setPrice(price);

        return dao.update(book);
    }

    public boolean updatePrice(int id, long price) {
        if (id <= 0) return false;
        return dao.updatePrice(id, Math.max(0, price));
    }

    /* ========= DELETE ========= */
    public boolean deleteBook(int id) {
        if (id <= 0) return false;
        return dao.delete(id);
    }
}
