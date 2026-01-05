package vnua.fita.vxlam.bookshop.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vnua.fita.vxlam.bookshop.dto.request.BookRequestDto;
import vnua.fita.vxlam.bookshop.dto.response.BookDto;



public interface BookService {
    Page<BookDto> findAll(Pageable pageable);
    BookDto findById(Long id);
    BookDto createBook(BookRequestDto requestDto);
    BookDto updateBook(Long id, BookRequestDto requestDto);
    void softDeleteBook(Long id);
    void restoreBook(Long id);
}
