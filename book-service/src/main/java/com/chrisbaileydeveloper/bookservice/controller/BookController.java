package com.chrisbaileydeveloper.bookservice.controller;

import com.chrisbaileydeveloper.bookservice.dto.BookRequest;
import com.chrisbaileydeveloper.bookservice.dto.BookResponse;
import com.chrisbaileydeveloper.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @MutationMapping
    public BookResponse createBook(@Argument BookRequest bookRequest) {
        bookService.createBook(bookRequest);
        return bookService.getAllBooks().stream()
                .filter(book -> book.getName().equals(bookRequest.getName()))
                .findFirst()
                .orElse(null);
    }

    @QueryMapping
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    @MutationMapping
    public boolean deleteBook(@Argument String id) {
        return bookService.deleteBook(id);
    }
}
