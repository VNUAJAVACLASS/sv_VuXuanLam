package vnua.fita.vxlam.bookshop.enums;

public enum OrderStatus {
    PENDING,        // Đang chờ xử lý
    CONFIRMED,      // Đã xác nhận
    SHIPPED,        // Đang giao hàng
    DELIVERED,      // Đã giao hàng
    CANCELLED       // Đã hủy
}