package vnua.fita.vxlam.bookshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vnua.fita.vxlam.bookshop.entity.Order;
import vnua.fita.vxlam.bookshop.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Page<Order> findByUser(User user, Pageable pageable);
}