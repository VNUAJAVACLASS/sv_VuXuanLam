package vnua.fita.vxlam.bookshop.controller;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vnua.fita.vxlam.bookshop.dto.request.OrderRequestDto;
import vnua.fita.vxlam.bookshop.dto.response.ApiResponse;
import vnua.fita.vxlam.bookshop.dto.response.OrderDto;
import vnua.fita.vxlam.bookshop.enums.OrderStatus;
import vnua.fita.vxlam.bookshop.service.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    public static UUID getCurrentUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Người dùng chưa đăng nhập");
        }

        Claims claims = (Claims) authentication.getDetails();
        return UUID.fromString(claims.get("userId", String.class));
    }


    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderDto>> checkout(
            @Valid @RequestBody OrderRequestDto requestDto) {

        UUID userId = getCurrentUserId();
        OrderDto newOrder = orderService.checkout(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Đặt hàng thành công", newOrder));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderDto>>> getAllOrders(
            @PageableDefault(size = 10, sort = "orderDate", direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable) {

        Page<OrderDto> orders = orderService.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách đơn hàng thành công", orders));
    }


    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<Page<OrderDto>>> getMyOrders(
            @PageableDefault(size = 10, sort = "orderDate", direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable) {

        UUID userId = getCurrentUserId();
        Page<OrderDto> orders = orderService.findUserOrders(userId, pageable);

        return ResponseEntity.ok(ApiResponse.success("Lấy lịch sử đơn hàng thành công", orders));
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.findById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy chi tiết đơn hàng thành công", order));
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus newStatus) {

        UUID currentUserId = getCurrentUserId();

        OrderDto updatedOrder = orderService.updateStatus(id, newStatus, currentUserId);

        return ResponseEntity.ok(ApiResponse.success("Cập nhật trạng thái đơn hàng thành công", updatedOrder));
    }
}