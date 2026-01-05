package controller;

import model.Book;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String q = req.getParameter("q");

        // Lấy danh sách sách (có thể là mới nhất hoặc search)
        List<Book> books;
        if (q != null && !q.trim().isEmpty()) {
            books = bookService.searchByTitle(q.trim());
            req.setAttribute("totalResults", books.size());
        } else {
            // ví dụ: lấy 20 cuốn mới nhất
            books = bookService.getNewestBooks(20);
        }

        // GÁN ĐÚNG TÊN ATTRIBUTE MÀ JSP ĐANG DÙNG
        req.setAttribute("BookList", books);

        // nếu có phân trang thì set thêm page, totalPages ở đây

        // forward tới đúng JSP
        req.getRequestDispatcher("/WEB-INF/views/user-book-list.jsp")
           .forward(req, resp);
    }
}

