package vnua.fita.vxlam.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vnua.fita.vxlam.bookshop.entity.Book;

import java.time.LocalDateTime;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Query ("UPDATE Book b SET b.isDeleted = true, b.deletedAt = :deletedAt WHERE b.id = :bookId")
    void softDeleteById(@Param("bookId") Long bookId, @Param("deletedAt") LocalDateTime deletedAt);


    @Modifying
    @Query("UPDATE Book b SET b.isDeleted = false, b.deletedAt = null WHERE b.id = :bookId")
    void restoreById(@Param("bookId") Long bookId);
}
