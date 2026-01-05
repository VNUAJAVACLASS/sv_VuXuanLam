package controller;

import model.Invoice;
import model.Order;
import service.OrderService;
import service.VNPayService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.OrderDAO;

import java.io.IOException;
import java.util.List;


public class CheckoutServlet extends HttpServlet {

    private final VNPayService vnPayService = new VNPayService();
    private final OrderService orderService = new OrderService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Nếu giỏ hàng trống thì đá về /cart
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("cart") == null) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // Hiển thị trang nhập địa chỉ (detail.jsp của anh)
        req.getRequestDispatcher("/WEB-INF/views/detail.jsp")
           .forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        HttpSession session = req.getSession();

        try {
            // 1. Lấy dữ liệu từ form
            String paymentMethod = req.getParameter("paymentMethod");
            String fullname = req.getParameter("fullname");
            String address = req.getParameter("address");
            String phone = req.getParameter("phone");

            // 2. Lấy giỏ hàng từ session
            @SuppressWarnings("unchecked")
            List<model.CartItem> cart = (List<model.CartItem>) session.getAttribute("cart");
            if (cart == null || cart.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }

            // 3. Tính tổng tiền từ giỏ hàng (không tin client)
            long amount = 0;
            for (model.CartItem item : cart) {
                amount += item.getPriceSnapshot() * item.getQuantity();
            }

            if (amount <= 0) {
                throw new IllegalArgumentException("Tổng tiền phải lớn hơn 0");
            }

            // 4. Tạo Order từ giỏ hàng
            Order order = OrderService.createOrderFromSession(session, paymentMethod, amount);
            order.setFullname(fullname);
            order.setAddress(address);
            order.setPhone(phone);

            // 5. Tạo Invoice
            Invoice invoice = new Invoice();
            invoice.setAmount(amount);
            invoice.setMethod(paymentMethod);
            invoice.setStatus("PENDING");

            // 6. Lưu vào DB → trả về orderId + invoiceId
            OrderDAO.Result result = orderService.createOrder(order, invoice);

            // 7. Lưu thông tin vào session để hiển thị sau
            session.setAttribute("orderId", result.orderId);
            session.setAttribute("invoiceId", result.invoiceId);
            session.setAttribute("checkout_name", fullname);
            session.setAttribute("checkout_method", paymentMethod);
            session.setAttribute("checkout_amount", amount);

            // 8. Xử lý theo phương thức
            if ("VNPAY".equals(paymentMethod)) {
                String ipAddress = vnPayService.getClientIp(req);
                String orderInfo = "Thanh toan don hang #" + result.orderId;

                // Dùng orderId làm vnp_TxnRef (duy nhất)
                String paymentUrl = vnPayService.createPaymentUrl(
                        result.orderId, amount, orderInfo, ipAddress
                );

                resp.sendRedirect(paymentUrl);
                return;
            }

            if ("COD".equals(paymentMethod)) {
                orderService.updatePaymentStatus(result.orderId, "COD_PENDING", result.invoiceId, "PENDING");
                resp.sendRedirect(req.getContextPath() + "/payment-success");
                return;
            }

            throw new IllegalArgumentException("Phương thức thanh toán không hỗ trợ: " + paymentMethod);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Số tiền không hợp lệ");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " + e.getMessage());
        }
    }
}