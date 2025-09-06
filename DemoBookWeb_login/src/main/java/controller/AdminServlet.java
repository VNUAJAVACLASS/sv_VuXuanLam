package controller;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import model.Book;
import service.BookService;
import java.util.List;

public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BookService bookService = new BookService();

    
    private boolean ensureLoggedIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        String user = (session != null) ? (String) session.getAttribute("username") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login"); 
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!ensureLoggedIn(req, resp)) return; 

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                req.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req, resp);
                break;

            case "edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                Book edit = bookService.findById(id);
                req.setAttribute("Book", edit);
                req.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req, resp);
                break;
            }

            case "delete": {
                int id = Integer.parseInt(req.getParameter("id"));
                bookService.deleteBook(id);
                resp.sendRedirect(req.getContextPath() + "/adminHome");
                break;
            }

            case "detail": {
                int id = Integer.parseInt(req.getParameter("id"));
                Book detail = bookService.findById(id);
                req.setAttribute("Book", detail);
                req.getRequestDispatcher("/WEB-INF/views/detail.jsp").forward(req, resp);
                break;
            }
            default:
                List<Book> list = bookService.getAllBook();
                req.setAttribute("BookList", list);
                req.setAttribute("user", req.getSession().getAttribute("username")); 
                req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!ensureLoggedIn(req, resp)) return; // ⬅️ chặn chưa login

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String idStr = req.getParameter("id");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        if (idStr == null || idStr.isEmpty()) {
            bookService.addBook(new Book(title, content));
        } else {
            int id = Integer.parseInt(idStr);
            Book existing = bookService.findById(id);
            if (existing != null) {
                existing.setTitle(title);
                existing.setContent(content);
                bookService.updateBook(existing);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/adminHome");
    }
}
