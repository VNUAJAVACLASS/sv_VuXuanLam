package vnua.fita.vxlam.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vnua.fita.vxlam.bookshop.entity.Book;
import vnua.fita.vxlam.bookshop.entity.Cart;
import vnua.fita.vxlam.bookshop.entity.CartItem;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndBook(Cart cart, Book book);
}