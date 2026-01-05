package controller;

import dao.BookDAO;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int page = 1;
        int pageSize = 9;

        // lấy param page
        String p = req.getParameter("page");
        if (p != null) {
            try { page = Integer.parseInt(p); } catch (Exception ignored) {}
        }

        // Lấy toàn bộ sách có phân trang
        int totalRows = bookDAO.countAllBooks();
        List<Book> books = bookDAO.getBooksPage(page, pageSize);

        int totalPages = (int) Math.ceil(totalRows * 1.0 / pageSize);

        req.setAttribute("BookList", books);
        req.setAttribute("page", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
    }
}
