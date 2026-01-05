package vnua.fita.vxlam.bookshop.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vnua.fita.vxlam.bookshop.dto.response.BookDto;
import vnua.fita.vxlam.bookshop.dto.request.BookRequestDto;
import vnua.fita.vxlam.bookshop.dto.response.ApiResponse;
import vnua.fita.vxlam.bookshop.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookDto>>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        Sort sortObj = Sort.by(
                sort.split(",")[1].equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                sort.split(",")[0]
        );

        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page<BookDto> books = bookService.findAll(pageable);

        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách sách thành công", books));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable Long id) {
        BookDto book = bookService.findById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy chi tiết sách thành công", book));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDto>> createBook(@Valid @RequestBody BookRequestDto requestDto) {
        BookDto newBook = bookService.createBook(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Thêm sách mới thành công", newBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> updateBook(@PathVariable Long id,
                                                           @Valid @RequestBody BookRequestDto requestDto) {
        BookDto updatedBook = bookService.updateBook(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật sách thành công", updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> softDeleteBook(@PathVariable Long id) {
        bookService.softDeleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa sách thành công", null));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreBook(@PathVariable Long id) {
        bookService.restoreBook(id);
        return ResponseEntity.ok(ApiResponse.success("Khôi phục sách thành công", null));
    }
}