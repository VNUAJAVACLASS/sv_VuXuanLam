package vnua.fita.vxlam.bookshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vnua.fita.vxlam.bookshop.dto.request.OrderRequestDto;
import vnua.fita.vxlam.bookshop.dto.response.OrderDto;
import vnua.fita.vxlam.bookshop.enums.OrderStatus;

import java.util.UUID;

public interface OrderService {

    //Tao order từ giỏ hàng
    OrderDto checkout(UUID userId, OrderRequestDto requestDto);

    //Lấy phân trang đơn hàng(admin)
    Page<OrderDto> findAll(Pageable pageable);
    OrderDto findById(Long orderId);
    OrderDto updateStatus(Long orderId, OrderStatus newStatus, UUID currentUserId);

    //Lấy ra phân trang của  đơn hàng của user
    Page<OrderDto> findUserOrders(UUID userId, Pageable pageable);
}
