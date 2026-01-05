package vnua.fita.vxlam.bookshop.dto.response;

import lombok.Builder;
import lombok.Getter;
import vnua.fita.vxlam.bookshop.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Builder
public class OrderDto {
    private Long id;
    private UUID userId;
    private String username;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private String phoneNumber;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<OrderBookDto> orderBooks;
}