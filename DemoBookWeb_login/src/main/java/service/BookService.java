package service;

import java.util.List;

import dao.BookDAO;
import model.Book;

public class BookService {
	private BookDAO dao;

	public BookService() {
		dao = new BookDAO();
	}

	public List<Book> getAllBook() {
		return dao.getAllBook();
	}

	public boolean addBook(Book Book) {
		return dao.addBook(Book);
	}

	public Book findById(int id) {
		return dao.findById(id);
	}

	public boolean updateBook(Book Book) {
		return dao.update(Book);
	}

	public boolean deleteBook(int id) {
		return dao.delete(id);
	}
}
