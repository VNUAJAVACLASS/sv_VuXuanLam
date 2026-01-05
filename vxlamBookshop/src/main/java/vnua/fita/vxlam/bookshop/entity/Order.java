package vnua.fita.vxlam.bookshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vnua.fita.vxlam.bookshop.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tbl_order")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết n-1 với User (Người đặt hàng)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    // Trường trạng thái
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    // Tổng tiền
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    // Các cuốn sách trong đơn hàng (Chi tiết đơn hàng)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderBook> orderBooks;
}