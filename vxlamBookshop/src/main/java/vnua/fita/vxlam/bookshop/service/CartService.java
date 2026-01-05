package vnua.fita.vxlam.bookshop.service;

import vnua.fita.vxlam.bookshop.dto.request.CartItemRequestDto;
import vnua.fita.vxlam.bookshop.dto.response.CartDto;

import java.util.UUID;

public interface CartService {

    CartDto getOrCreateCart(UUID userId);

    CartDto addItemToCart(UUID userId, CartItemRequestDto requestDto);

    CartDto updateItemQuantity(UUID userId, CartItemRequestDto requestDto);

    CartDto removeItemFromCart(UUID userId, Long bookId);

    void clearCart(UUID userId);
}