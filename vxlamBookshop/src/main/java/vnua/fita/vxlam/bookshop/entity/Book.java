package vnua.fita.vxlam.bookshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="tbl_book")
@Where(clause = "is_deleted = false")
@Getter @Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;//Tiêu đề

    private String author;//Tác giả

    @Column(columnDefinition = "TEXT")
    private String description;//Mô tả

    private BigDecimal price;//Giá tiền

    private Integer stock;//Tồn kho

    private String imgUrl;//Ảnh bìa sách

    private String publisher;//Nhà xuất bản

    private String isbn;//Mã isbn sách có độ dài theo tiêu chuẩn là 13 chữ số

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Tự động gán ngày tạo khi lưu sách mới
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
