package vnua.fita.vxlam.bookshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.fita.vxlam.bookshop.dto.request.CartItemRequestDto;
import vnua.fita.vxlam.bookshop.dto.response.CartDto;
import vnua.fita.vxlam.bookshop.dto.response.CartItemDto;
import vnua.fita.vxlam.bookshop.entity.Book;
import vnua.fita.vxlam.bookshop.entity.Cart;
import vnua.fita.vxlam.bookshop.entity.CartItem;
import vnua.fita.vxlam.bookshop.entity.User;
import vnua.fita.vxlam.bookshop.repository.BookRepository;
import vnua.fita.vxlam.bookshop.repository.CartRepository;
import vnua.fita.vxlam.bookshop.repository.UserRepository;
import vnua.fita.vxlam.bookshop.service.CartService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository,
                           BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    private CartItemDto toCartItemDto(CartItem item) {
        return CartItemDto.builder()
                .id(item.getId())
                .bookId(item.getBook().getId())
                .bookTitle(item.getBook().getTitle())
                .quantity(item.getQuantity())
                .price(item.getBook().getPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }

    private CartDto toCartDto(Cart cart) {
        List<CartItemDto> items = cart.getCartItems().stream()
                .map(this::toCartItemDto)
                .collect(Collectors.toList());

        BigDecimal totalCartAmount = items.stream()
                .map(CartItemDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartDto.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .cartItems(items)
                .totalAmount(totalCartAmount)
                .build();
    }

    private Cart getCartEntity(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy giỏ hàng cho người dùng này."));
    }

    @Override
    public CartDto getOrCreateCart(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng với ID: " + userId));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartItems(new HashSet<>());
                    return cartRepository.save(newCart);
                });

        return toCartDto(cart);
    }

    @Override
    @Transactional
    public CartDto addItemToCart(UUID userId, CartItemRequestDto requestDto) {
        getOrCreateCart(userId);
        Cart cart = getCartEntity(userId);

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sách với ID: " + requestDto.getBookId()));

        if (book.getStock() < requestDto.getQuantity()) {
            throw new IllegalArgumentException(
                    String.format("Sách '%s' chỉ còn %d cuốn trong kho, không đủ số lượng %d.",
                            book.getTitle(), book.getStock(), requestDto.getQuantity()));
        }

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(requestDto.getBookId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + requestDto.getQuantity();
            if (book.getStock() < newQuantity) {
                throw new IllegalArgumentException("Không thể thêm vào giỏ hàng: số lượng vượt quá tồn kho.");
            }
            existingItem.setQuantity(newQuantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setBook(book);
            newItem.setQuantity(requestDto.getQuantity());
            cart.getCartItems().add(newItem);
        }

        Cart updatedCart = cartRepository.save(cart);
        return toCartDto(updatedCart);
    }

    @Override
    @Transactional
    public CartDto updateItemQuantity(UUID userId, CartItemRequestDto requestDto) {
        Cart cart = getCartEntity(userId);

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(requestDto.getBookId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sách trong giỏ hàng để cập nhật."));

        Book book = existingItem.getBook();
        int newQuantity = requestDto.getQuantity();

        if (newQuantity <= 0) {
            cart.getCartItems().remove(existingItem);
        } else if (book.getStock() < newQuantity) {
            throw new IllegalArgumentException(
                    String.format("Sách '%s' chỉ còn %d cuốn trong kho, không thể đặt số lượng %d.",
                            book.getTitle(), book.getStock(), newQuantity));
        } else {
            existingItem.setQuantity(newQuantity);
        }

        Cart updatedCart = cartRepository.save(cart);
        return toCartDto(updatedCart);
    }

    @Override
    @Transactional
    public CartDto removeItemFromCart(UUID userId, Long bookId) {
        Cart cart = getCartEntity(userId);

        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sách trong giỏ hàng để xóa."));

        cart.getCartItems().remove(itemToRemove);

        Cart updatedCart = cartRepository.save(cart);
        return toCartDto(updatedCart);
    }

    @Override
    @Transactional
    public void clearCart(UUID userId) {
        Cart cart = getCartEntity(userId);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}