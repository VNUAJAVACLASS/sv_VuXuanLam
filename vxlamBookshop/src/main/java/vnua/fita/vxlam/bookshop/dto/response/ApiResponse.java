package vnua.fita.vxlam.bookshop.dto.response;

import lombok.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ApiResponse<T> {
    private String message;
    private String status;
    private T data;

    // Phương thức trả về thành công
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .message(message)
                .data(data)

                .build();
    }

    // Phương thức trả về lỗi
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .status("error")
                .message(message)
                .data(null)
                .build();
    }

}
