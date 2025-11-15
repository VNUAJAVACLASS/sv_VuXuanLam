package controller;

import model.Book;
import model.CartItem;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CartServlet extends HttpServlet {

    private final BookService bookService = new BookService(); 
    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private Integer getIntParam(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        if (v == null) return null;
        v = v.trim();
        if (v.isEmpty()) return null;
        try { return Integer.valueOf(v); } catch (NumberFormatException e) { return null; }
    }

    private int getValidQty(HttpServletRequest req, String name) {
        Integer q = getIntParam(req, name);
        return (q == null || q <= 0) ? 1 : q;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "add";

        HttpSession session = req.getSession();
        List<CartItem> cart = getCart(session);

        switch (action) {
            case "add": {
                Integer id = getIntParam(req, "id");
                if (id == null) { resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu hoặc sai định dạng id"); return; }

                int qty = getValidQty(req, "qty");
                Book b = bookService.findById(id);
                if (b != null) {
                    boolean found = false;
                    for (CartItem it : cart) {
                        if (it.getBook().getId() == id) {
                            it.setQuantity(it.getQuantity() + qty);
                            found = true; break;
                        }
                    }
                    if (!found) {
                        long price = bookService.getPriceById(id); // đọc từ DB book
                        cart.add(new CartItem(b, qty, price));
                    }
                }
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            case "update": {
                Integer id = getIntParam(req, "id");
                if (id == null) { resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu hoặc sai định dạng id"); return; }
                int qty = getValidQty(req, "qty");
                for (CartItem it : cart) {
                    if (it.getBook().getId() == id) { it.setQuantity(qty); break; }
                }
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            case "remove": {
                Integer id = getIntParam(req, "id");
                if (id == null) { resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu hoặc sai định dạng id"); return; }
                cart.removeIf(it -> it.getBook().getId() == id);
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            case "clear": {
                session.removeAttribute("cart");
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            default:
                resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
