package vnua.fita.vxlam.bookshop.dto.response;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Giá tiền phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được âm")
    private Integer stock;
    private String imgUrl;
    private String publisher;

    @NotBlank(message = "ISBN không được để trống")
    @Size(min = 13, max = 13, message = "Mã ISBN phải đúng 13 ký tự")
    private String isbn;
    private LocalDateTime createdAt;
}