package com.chrisbaileydeveloper.bookservice.controller;

import com.chrisbaileydeveloper.bookservice.dto.BookRequest;
import com.chrisbaileydeveloper.bookservice.dto.BookResponse;
import com.chrisbaileydeveloper.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody BookRequest bookRequest) {
        bookService.createBook(bookRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        boolean isDeleted = bookService.deleteBook(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }
}
