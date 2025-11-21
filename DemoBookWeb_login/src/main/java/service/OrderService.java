package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import dao.OrderDAO;
import model.CartItem;
import model.Invoice;
import model.Order;
import model.OrderItem;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();

    /**
     * Tạo đơn hàng + chi tiết + hóa đơn trong 1 transaction
     */
    public OrderDAO.Result createOrder(Order order, Invoice invoice) {
        validateOrder(order);
        validateInvoice(invoice);

        try {
            return orderDAO.createOrderWithItemsAndInvoice(order, invoice);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tạo đơn hàng: " + e.getMessage(), e);
        }
    }

    /**
     * Cập nhật trạng thái thanh toán sau khi VNPay/ZaloPay trả về
     */
    public void updatePaymentStatus(
            int orderId, String orderStatus,
            int invoiceId, String invoiceStatus) {

        if (orderId <= 0 || invoiceId <= 0) {
            throw new IllegalArgumentException("ID đơn hàng hoặc hóa đơn không hợp lệ");
        }

        try {
            orderDAO.updatePaymentStatus(orderId, orderStatus, invoiceId, invoiceStatus);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật trạng thái thanh toán: " + e.getMessage(), e);
        }
    }

   
    private void validateOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Đơn hàng không được null");
        if (order.getFullname() == null || order.getFullname().trim().isEmpty())
            throw new IllegalArgumentException("Họ tên không được để trống");
        if (order.getPhone() == null || !order.getPhone().matches("\\d{10,11}"))
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        if (order.getAddress() == null || order.getAddress().trim().isEmpty())
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        if (order.getTotalAmount() <= 0)
            throw new IllegalArgumentException("Tổng tiền phải lớn hơn 0");

        List<OrderItem> items = order.getItems();
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("Giỏ hàng trống");
    }

    /**
     * Kiểm tra hóa đơn hợp lệ
     */
    private void validateInvoice(Invoice invoice) {
        if (invoice == null) throw new IllegalArgumentException("Hóa đơn không được null");
        if (invoice.getAmount() <= 0)
            throw new IllegalArgumentException("Số tiền hóa đơn phải lớn hơn 0");
        if (!List.of("COD", "VNPAY", "ZaloPay").contains(invoice.getMethod()))
            throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ");
    }

 
    public static Order createOrderFromSession(
            HttpSession session,
            String paymentMethod,
            double totalAmount) {

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng trống");
        }

        Order order = new Order();
        order.setUserId(getUserIdFromSession(session));
        order.setFullname((String) session.getAttribute("checkout_fullname"));
        order.setPhone((String) session.getAttribute("checkout_phone"));
        order.setAddress((String) session.getAttribute("checkout_address"));
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cart) {
            OrderItem oi = new OrderItem();
            oi.setProductId(ci.getBook().getId());
            oi.setProductName(ci.getBook().getTitle());
            oi.setUnitPrice(ci.getPriceSnapshot());
            oi.setQuantity(ci.getQuantity());
            oi.setLineTotal(ci.getPriceSnapshot() * ci.getQuantity());
            orderItems.add(oi);
        }
        order.setItems(orderItems);

        return order;
    }

    private static Integer getUserIdFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return userId != null ? userId : 1; // guest
    }    public void confirmVNPayPayment(int orderId, int invoiceId) {
        updatePaymentStatus(orderId, "PAID", invoiceId, "SUCCESS");
    }

    public void failVNPayPayment(int orderId, int invoiceId) {
        updatePaymentStatus(orderId, "FAILED", invoiceId, "FAILED");
    }
    public List<Order> getOrdersOfUser(int userId) {
        try {
            return orderDAO.findByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy danh sách đơn hàng của user: " + e.getMessage(), e);
        }
    }

    public List<Order> getAllOrders() {
        try {
            return orderDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy danh sách đơn hàng: " + e.getMessage(), e);
        }
    }

    public Order getOrderDetail(int orderId) {
        try {
            return orderDAO.findByIdWithItems(orderId);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy chi tiết đơn hàng: " + e.getMessage(), e);
        }
    }
    
    public List<Order> getOrdersByUserId(int userId) {
        try {
            return orderDAO.findByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy đơn theo userId: " + e.getMessage(), e);
        }
    }

    public Order getOrderById(int orderId) {
        try {
            return orderDAO.findById(orderId);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi lấy đơn hàng theo ID: " + e.getMessage(), e);
        }
    }
    
    public List<OrderItem> getOrderItems(int orderId) {
        try {
            return orderDAO.getOrderItems(orderId);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi lấy danh sách sản phẩm của đơn hàng: " + e.getMessage(), e);
        }
    }
    
    public Invoice getInvoiceByOrderId(int orderId) {
        try {
            return orderDAO.findInvoiceByOrderId(orderId);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi lấy hóa đơn: " + e.getMessage(), e);
        }
    }
    
}