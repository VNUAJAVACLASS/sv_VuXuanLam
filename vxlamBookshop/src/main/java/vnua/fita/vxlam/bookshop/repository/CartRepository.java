package vnua.fita.vxlam.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vnua.fita.vxlam.bookshop.entity.Cart;
import vnua.fita.vxlam.bookshop.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    Optional<Cart> findByUserId(UUID userId);


    Optional<Cart> findByUser(User user);
}