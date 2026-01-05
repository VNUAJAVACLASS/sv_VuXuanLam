package vnua.fita.vxlam.bookshop.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter @Builder
public class CartItemDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private BigDecimal price; // Giá bán của cuốn sách
    private BigDecimal totalPrice; // Tổng tiền = quantity * price
}