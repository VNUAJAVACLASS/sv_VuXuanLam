package vnua.fita.vxlam.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vnua.fita.vxlam.bookshop.entity.OrderBook;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
}
