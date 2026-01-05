package vnua.fita.vxlam.bookshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.fita.vxlam.bookshop.dto.response.BookDto;
import vnua.fita.vxlam.bookshop.dto.request.BookRequestDto;
import vnua.fita.vxlam.bookshop.entity.Book;
import vnua.fita.vxlam.bookshop.repository.BookRepository;
import vnua.fita.vxlam.bookshop.service.BookService; // Import Interface

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(this::convertToDto);
    }


    // --- 2. Lấy sách theo ID ---
    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with ID: " + id));
        return convertToDto(book);
    }

    // --- 3. Thêm mới sách ---
    @Override
    public BookDto createBook(BookRequestDto requestDto) {
        Book book = convertToEntity(requestDto);
        Book savedBook = bookRepository.save(book);
        return convertToDto(savedBook);
    }

    // --- 4. Cập nhật sách ---
    @Override
    public BookDto updateBook(Long id, BookRequestDto requestDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with ID: " + id));

        // Cập nhật thông tin
        existingBook.setTitle(requestDto.getTitle());
        existingBook.setAuthor(requestDto.getAuthor());
        existingBook.setDescription(requestDto.getDescription());
        existingBook.setPrice(requestDto.getPrice());
        existingBook.setStock(requestDto.getStock());
        existingBook.setImgUrl(requestDto.getImgUrl());
        existingBook.setPublisher(requestDto.getPublisher());
        existingBook.setIsbn(requestDto.getIsbn());

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDto(updatedBook);
    }

    // --- 5. Xóa mềm (Soft Delete) ---
    @Override
    public void softDeleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NoSuchElementException("Book not found with ID: " + id);
        }
        bookRepository.softDeleteById(id, LocalDateTime.now());
    }

    // --- 6. Khôi phục (Restore) ---
    @Override
    public void restoreBook(Long id) {
        bookRepository.restoreById(id);
    }



    private BookDto convertToDto(Book book) {
        // (Sử dụng lại phương thức mapping đã có)
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setPrice(book.getPrice());
        dto.setStock(book.getStock());
        dto.setImgUrl(book.getImgUrl());
        dto.setPublisher(book.getPublisher());
        dto.setIsbn(book.getIsbn());
        dto.setCreatedAt(book.getCreatedAt());
        return dto;
    }

    private Book convertToEntity(BookRequestDto dto) {
        // (Sử dụng lại phương thức mapping đã có)
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        book.setPrice(dto.getPrice());
        book.setStock(dto.getStock());
        book.setImgUrl(dto.getImgUrl());
        book.setPublisher(dto.getPublisher());
        book.setIsbn(dto.getIsbn());
        book.setIsDeleted(false);
        return book;
    }
}