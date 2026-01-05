package vnua.fita.vxlam.bookshop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vnua.fita.vxlam.bookshop.dto.request.OrderRequestDto;
import vnua.fita.vxlam.bookshop.dto.response.OrderBookDto;
import vnua.fita.vxlam.bookshop.dto.response.OrderDto;
import vnua.fita.vxlam.bookshop.entity.*;
import vnua.fita.vxlam.bookshop.enums.OrderStatus;
import vnua.fita.vxlam.bookshop.repository.*;
import vnua.fita.vxlam.bookshop.service.OrderService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional // Đảm bảo tất cả các thao tác (tạo order, trừ tồn kho, xóa cart) đều thành công
    public OrderDto checkout(UUID userId, OrderRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng với ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy giỏ hàng cho người dùng này"));

        Set<CartItem> items = cart.getCartItems();

        if (items.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng rỗng, không thể tạo đơn hàng.");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        Set<OrderBook> orderBooks = new HashSet<>();

        // 1. Kiểm tra tồn kho và tính tổng tiền
        for (CartItem item : items) {
            Book book = item.getBook();
            int requiredQuantity = item.getQuantity();

            if (book.getStock() < requiredQuantity) {
                throw new IllegalArgumentException(
                        String.format("Sách '%s' chỉ còn %d cuốn trong kho, không đủ số lượng %d.",
                                book.getTitle(), book.getStock(), requiredQuantity));
            }

            // Tính tổng tiền
            totalAmount = totalAmount.add(book.getPrice().multiply(BigDecimal.valueOf(requiredQuantity)));

            // Tạo chi tiết đơn hàng (OrderBook)
            OrderBook orderBook = new OrderBook();
            orderBook.setBook(book);
            orderBook.setQuantity(requiredQuantity);
            orderBook.setPriceAtOrder(book.getPrice());
            orderBooks.add(orderBook);
        }

        // 2. Tạo Entity Order
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setPhoneNumber(requestDto.getPhoneNumber());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);

        // Liên kết OrderBook với Order
        for (OrderBook ob : orderBooks) {
            ob.setOrder(order);
        }
        order.setOrderBooks(orderBooks);

        Order savedOrder = orderRepository.save(order);

        // 3. Cập nhật tồn kho và xóa CartItem (chỉ khi Order đã được lưu thành công)
        for (CartItem item : items) {
            Book book = item.getBook();
            book.setStock(book.getStock() - item.getQuantity()); // Trừ tồn kho
            bookRepository.save(book);
        }


        items.clear();
        cartRepository.save(cart);

        return toOrderDto(savedOrder);
    }

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(this::toOrderDto);
    }

    @Override
    public OrderDto findById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy đơn hàng với ID: " + orderId));
        return toOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateStatus(Long orderId, OrderStatus newStatus, UUID currentUserId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy đơn hàng với ID: " + orderId));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng hiện tại"));



        //Phân quyền cho chức năng cập nhật trạng thái, user thì chỉ có huỷ và thành công, còn admin thì có đầy đủ quyền
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));


        if (isAdmin) {


        } else {
            // USER: Bắt buộc phải là chủ đơn hàng
            if (!order.getUser().getId().equals(currentUserId)) {
                throw new SecurityException("Bạn không có quyền thay đổi trạng thái đơn hàng này.");
            }

            //USER: Chỉ được phép HỦY (CANCELLED) nếu đơn hàng chưa được xác nhận (PENDING)
            if (newStatus == OrderStatus.CANCELLED) {
                //Chỉ cho phép hủy nếu trạng thái hiện tại là PENDING
                if (order.getStatus() != OrderStatus.PENDING) {
                    throw new IllegalArgumentException("Không thể hủy đơn hàng đã được xác nhận.");
                }
            } else if (newStatus == OrderStatus.DELIVERED) {

                if (order.getStatus() != OrderStatus.SHIPPED) {
                    throw new IllegalArgumentException("Đơn hàng chưa được vận chuyển, không thể xác nhận đã nhận.");
                }
            } else {

                throw new SecurityException("Bạn không có quyền chuyển đơn hàng sang trạng thái này.");
            }
        }

        // Tiến hành cập nhật trạng thái
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        return toOrderDto(updatedOrder);
    }


    @Override
    public Page<OrderDto> findUserOrders(UUID userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng với ID: " + userId));

        return orderRepository.findByUser(user, pageable).map(this::toOrderDto);
    }




    //Map, dung mapstruct

    private OrderBookDto toOrderBookDto(OrderBook orderBook) {
        return OrderBookDto.builder()
                .bookId(orderBook.getBook().getId())
                .bookTitle(orderBook.getBook().getTitle())
                .quantity(orderBook.getQuantity())
                .priceAtOrder(orderBook.getPriceAtOrder())
                .build();
    }

    private OrderDto toOrderDto(Order order) {
        List<OrderBookDto> orderBookDtos = order.getOrderBooks().stream()
                .map(this::toOrderBookDto)
                .collect(Collectors.toList());

        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .username(order.getUser().getUsername())
                .orderDate(order.getOrderDate())
                .shippingAddress(order.getShippingAddress())
                .phoneNumber(order.getPhoneNumber())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .orderBooks(orderBookDtos)
                .build();
    }

}
