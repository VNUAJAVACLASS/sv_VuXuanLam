package controller;

import service.OrderService;
import service.VNPayService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

public class PaymentReturnServlet extends HttpServlet {

    private final VNPayService vnPayService = new VNPayService();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Map<String, String> params = vnPayService.extractParameters(req);

        try {
            // 1. Kiểm tra chữ ký (bắt buộc)
            if (!vnPayService.validateChecksum(params)) {
                req.setAttribute("error", "Chữ ký VNPay không hợp lệ (có thể bị giả mạo)");
                forwardToFail(req, resp);
                return;
            }

            // 2. Lấy dữ liệu từ session (đã lưu ở CheckoutServlet)
            Integer orderIdObj = (Integer) session.getAttribute("orderId");
            Integer invoiceIdObj = (Integer) session.getAttribute("invoiceId");

            if (orderIdObj == null || invoiceIdObj == null) {
                req.setAttribute("error", "Không tìm thấy thông tin đơn hàng");
                forwardToFail(req, resp);
                return;
            }

            int orderId = orderIdObj;
            int invoiceId = invoiceIdObj;

            // 3. Kiểm tra vnp_TxnRef có khớp với orderId không (chống giả mạo)
            String txnRef = params.get("vnp_TxnRef");
            if (txnRef == null || !txnRef.startsWith(String.valueOf(orderId))) {
                req.setAttribute("error", "Mã giao dịch không khớp với đơn hàng");
                forwardToFail(req, resp);
                return;
            }

            // 4. Kiểm tra trạng thái thanh toán
            if (vnPayService.isPaymentSuccess(params)) {
                // Cập nhật DB: order + invoice
                orderService.updatePaymentStatus(orderId, "PAID", invoiceId, "SUCCESS");

                // Xóa giỏ hàng & thông tin tạm
                session.removeAttribute("cart");
                session.removeAttribute("totalPrice");
                session.removeAttribute("orderId");
                session.removeAttribute("invoiceId");

                // Chuyển đến trang thành công
                req.setAttribute("orderId", orderId);
                req.getRequestDispatcher("/WEB-INF/views/payment-success.jsp").forward(req, resp);

            } else {
                // Thanh toán thất bại
                String msg = vnPayService.getResponseMessage(params);
                orderService.updatePaymentStatus(orderId, "FAILED", invoiceId, "FAILED");

                req.setAttribute("error", msg);
                forwardToFail(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            forwardToFail(req, resp);
        }
    }

    // Helper: Chuyển hướng đến trang thất bại
    private void forwardToFail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/payment-fail.jsp").forward(req, resp);
    }
}