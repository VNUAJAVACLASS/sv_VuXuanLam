package controller;

import model.Book;
import model.CartItem;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Hiển thị giỏ
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
                int id = Integer.parseInt(req.getParameter("id"));
                int qty = 1;
                try { qty = Math.max(1, Integer.parseInt(req.getParameter("qty"))); } catch (Exception ignored) {}
                Book b = bookService.findById(id);
                if (b != null) {
                    boolean found = false;
                    for (CartItem it : cart) {
                        if (it.getBook().getId() == id) {
                            it.setQuantity(it.getQuantity() + qty);
                            found = true; break;
                        }
                    }
                    if (!found) cart.add(new CartItem(b, qty));
                }
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            case "update": {
                int id = Integer.parseInt(req.getParameter("id"));
                int qty = Math.max(1, Integer.parseInt(req.getParameter("qty")));
                cart.removeIf(it -> {
                    if (it.getBook().getId() == id) { it.setQuantity(qty); }
                    return false;
                });
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            case "remove": {
                int id = Integer.parseInt(req.getParameter("id"));
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
