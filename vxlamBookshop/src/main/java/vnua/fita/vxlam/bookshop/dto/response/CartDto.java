package vnua.fita.vxlam.bookshop.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID; // Sử dụng UUID cho userId

@Getter @Builder
public class CartDto {
    private Long id;
    private UUID userId; // ID của người dùng sở hữu giỏ hàng
    private List<CartItemDto> cartItems;
    private BigDecimal totalAmount; // Tổng tiền của tất cả các mặt hàng trong giỏ hàng
}