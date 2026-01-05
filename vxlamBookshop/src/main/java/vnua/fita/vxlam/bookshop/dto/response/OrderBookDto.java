package vnua.fita.vxlam.bookshop.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderBookDto {
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private BigDecimal priceAtOrder;
}