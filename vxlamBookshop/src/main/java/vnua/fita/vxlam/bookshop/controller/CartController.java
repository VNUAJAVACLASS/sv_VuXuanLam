package vnua.fita.vxlam.bookshop.controller;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vnua.fita.vxlam.bookshop.dto.request.CartItemRequestDto;
import vnua.fita.vxlam.bookshop.dto.response.ApiResponse;
import vnua.fita.vxlam.bookshop.dto.response.CartDto;
import vnua.fita.vxlam.bookshop.service.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    public static UUID getCurrentUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Người dùng chưa đăng nhập");
        }

        Claims claims = (Claims) authentication.getDetails();
        return UUID.fromString(claims.get("userId", String.class));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<CartDto>> getCart() {
        UUID userId = getCurrentUserId();
        CartDto cart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(ApiResponse.success("Lấy giỏ hàng thành công", cart));
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartDto>> addItemToCart(@Valid @RequestBody CartItemRequestDto requestDto) {
        UUID userId = getCurrentUserId();
        CartDto cart = cartService.addItemToCart(userId, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Thêm sách vào giỏ hàng thành công", cart));
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CartDto>> updateItemQuantity(@Valid @RequestBody CartItemRequestDto requestDto) {
        UUID userId = getCurrentUserId();
        // Phương thức này dùng chung cho việc thay đổi số lượng
        CartDto cart = cartService.updateItemQuantity(userId, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật số lượng thành công", cart));
    }


    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<ApiResponse<CartDto>> removeItemFromCart(@PathVariable Long bookId) {
        UUID userId = getCurrentUserId();
        CartDto cart = cartService.removeItemFromCart(userId, bookId);
        return ResponseEntity.ok(ApiResponse.success("Xóa sách khỏi giỏ hàng thành công", cart));
    }


    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        UUID userId = getCurrentUserId();
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success("Xóa toàn bộ giỏ hàng thành công", null));
    }
}