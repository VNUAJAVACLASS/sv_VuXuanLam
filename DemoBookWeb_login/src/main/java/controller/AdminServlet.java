package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import model.Book;
import service.BookService;


public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BookService bookService = new BookService();

    /* -------- Helpers -------- */
    private boolean ensureLoggedIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        String user = (session != null) ? (String) session.getAttribute("username") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    private Integer getIntParam(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        if (v == null) return null;
        v = v.trim();
        if (v.isEmpty()) return null;
        try { return Integer.valueOf(v); } catch (NumberFormatException e) { return null; }
    }

    private Long getLongParam(HttpServletRequest req, String name, long defaultVal) {
        String v = req.getParameter(name);
        if (v == null) return defaultVal;
        v = v.trim();
        if (v.isEmpty()) return defaultVal;
        try { return Long.valueOf(v); } catch (NumberFormatException e) { return defaultVal; }
    }

    private void setupUtf8(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html;charset=UTF-8");
    }

    /* -------- GET -------- */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!ensureLoggedIn(req, resp)) return;
        setupUtf8(req, resp);

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create": {
                req.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req, resp);
                break;
            }
            case "edit": {
                Integer id = getIntParam(req, "id");
                if (id == null) { resp.sendRedirect(req.getContextPath() + "/adminHome"); return; }
                Book edit = bookService.findById(id);
                req.setAttribute("Book", edit);
                req.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req, resp);
                break;
            }
            case "delete": {
                Integer id = getIntParam(req, "id");
                if (id != null) { bookService.deleteBook(id); }
                resp.sendRedirect(req.getContextPath() + "/adminHome");
                break;
            }
            case "detail": {
                Integer id = getIntParam(req, "id");
                if (id == null) { resp.sendRedirect(req.getContextPath() + "/adminHome"); return; }
                Book detail = bookService.findById(id);
                req.setAttribute("Book", detail);
                req.getRequestDispatcher("/WEB-INF/views/detail.jsp").forward(req, resp);
                break;
            }
            default: {
                List<Book> list = bookService.getAllBook(); // nhớ trả về cả price trong service/dao
                req.setAttribute("BookList", list);
                req.setAttribute("user", req.getSession().getAttribute("username"));
                req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
                break;
            }
        }
    }

    /* -------- POST (create/update) -------- */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!ensureLoggedIn(req, resp)) return;
        setupUtf8(req, resp);

        String idStr = req.getParameter("id");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        long price = getLongParam(req, "price", 0L);  // ⚡ nhận giá từ form, mặc định 0

        // chống null
        title = (title == null) ? "" : title.trim();
        content = (content == null) ? "" : content.trim();

        if (idStr == null || idStr.trim().isEmpty()) {
            // create
            Book toAdd = new Book(title, content, price);
            bookService.addBook(toAdd);
        } else {
            // update
            Integer id = getIntParam(req, "id");
            if (id != null) {
                Book existing = bookService.findById(id);
                if (existing != null) {
                    existing.setTitle(title);
                    existing.setContent(content);
                    existing.setPrice(price);
                    bookService.updateBook(existing);
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/adminHome");
    }
}
