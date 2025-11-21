package controller;

import model.Invoice;
import model.Order;
import model.OrderItem;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/invoice")
public class InvoiceServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String orderIdStr = req.getParameter("orderId");
        if (orderIdStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu mã đơn hàng");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã đơn hàng không hợp lệ");
            return;
        }

        // Lấy đơn hàng
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn hàng");
            return;
        }

        // Nếu muốn check đúng user:
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("role") != null
                && !"ADMIN".equals(session.getAttribute("role"))) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null || !userId.equals(order.getUserId())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền xem đơn này");
                return;
            }
        }

        List<OrderItem> items = orderService.getOrderItems(orderId);
        Invoice invoice = orderService.getInvoiceByOrderId(orderId);

        req.setAttribute("order", order);
        req.setAttribute("items", items);
        req.setAttribute("invoice", invoice);

        req.getRequestDispatcher("/WEB-INF/views/order-invoice.jsp")
           .forward(req, resp);
    }
}
