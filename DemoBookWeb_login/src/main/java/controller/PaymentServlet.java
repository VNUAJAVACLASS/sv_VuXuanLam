package controller;

import service.VNPayService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


public class PaymentServlet extends HttpServlet {

    private VNPayService vnPayService = new VNPayService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            long amount = Long.parseLong(req.getParameter("amount"));

            String paymentUrl = vnPayService.createPaymentUrl(
                    orderId,
                    amount,
                    "Thanh toán đơn hàng #" + orderId,
                    req.getRemoteAddr()
            );

            resp.sendRedirect(paymentUrl);

        } catch (Exception e) {
            resp.getWriter().write("Lỗi khi tạo thanh toán: " + e.getMessage());
        }
    }
}

