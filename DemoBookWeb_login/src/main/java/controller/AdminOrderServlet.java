package controller;

import model.Order;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // nếu anh có check role admin trong session thì kiểm tra ở đây
        // if (!"ADMIN".equals(session.getAttribute("role"))) { ... }

        List<Order> orders = orderService.getAllOrders();
        req.setAttribute("orders", orders);

        req.getRequestDispatcher("/WEB-INF/views/admin-orders.jsp")
           .forward(req, resp);
    }
}
